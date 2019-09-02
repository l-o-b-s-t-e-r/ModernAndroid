package com.example.myapplication.data.repositories.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.data.UserBoundaryCallback
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.repositories.remote.IRemoteRepository
import com.example.myapplication.domain.NetworkState
import com.example.myapplication.domain.UserMapper
import com.example.myapplication.domain.dto.UserDto
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalRepository(
    private val db: AppDatabase,
    private val remoteRepository: IRemoteRepository,
    private val userMapper: UserMapper
) : ILocalRepository {

    override val userSearchQuery = MutableLiveData<String>().apply {
        postValue("")
    }

    private fun getTransformedUserSearchQuery(): String {
        val query = userSearchQuery.value ?: ""
        return "$query%"
    }

    override fun getAllUsersPerPage(): DataSource.Factory<Int, UserEntity> {
        return db.userDao().getAllUsersSortedByName()
    }

    override fun saveAllUsers(users: List<UserEntity>) {
        return db.userDao().saveAll(users)
    }

    override fun updateAllUsers(users: List<UserEntity>) {
        db.runInTransaction {
            db.userDao().deleteAll()
            saveAllUsers(users)
        }
    }

    override fun getAllUsers(): Flowable<List<UserEntity>> {
        return db.userDao().getAll()
    }

    override fun updateUserColor(id: String, color: Int): Completable {
        return db.userDao().updateColor(id, color)
    }

    override fun getAllUsersWithLimit(count: Int): List<UserEntity> {
        return db.userDao().getAllWithLimit(count)
    }

    override fun getAllUsersAfterWithLimit(userKey: String, count: Int): List<UserEntity> {
        return db.userDao().getAfterWithLimit(userKey, count)
    }

    override fun getAllUsersPerPage(
        config: PagedList.Config,
        networkState: MutableLiveData<NetworkState>
    ): LiveData<PagedList<UserDto>> {
        val boundaryCallback = UserBoundaryCallback(
            saveResponse = this::saveAllUsers,
            remoteRepository = remoteRepository,
            config = config,
            networkState = networkState,
            userMapper = userMapper,
            query = userSearchQuery.value ?: ""
        )

        return LivePagedListBuilder(
            db.userDao().getAllUsersByQuery(getTransformedUserSearchQuery()).map {
                userMapper.toDto(it)
            },
            config
        ).setBoundaryCallback(boundaryCallback).build()
    }

    override fun deleteUserById(id: String): Completable {
        return db.userDao().deleteById(id)
    }

    override fun deleteFirstUser(): Completable {
        return db.userDao().deleteFirst()
    }

    override fun showLastUser(): Completable {
        return db.userDao().showLast(getTransformedUserSearchQuery())
    }

    override fun hideFirstUser(): Completable {
        return db.userDao().hideFirst(getTransformedUserSearchQuery())
    }
}