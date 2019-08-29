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
import com.example.myapplication.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalRepository(
    private val db: AppDatabase,
    private val remoteRepository: IRemoteRepository
) : ILocalRepository {

    override fun getAllUsersPerPage(): DataSource.Factory<Int, UserEntity> {
        return db.userDao().getAllOrderByName()
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

    override fun getAllUsersPerPage(config: PagedList.Config, networkState: MutableLiveData<NetworkState>): LiveData<PagedList<UserEntity>> {
        val boundaryCallback = UserBoundaryCallback(
            saveResponse = this::saveAllUsers,
            remoteRepository = remoteRepository,
            config = config,
            networkState = networkState
        )

        return LivePagedListBuilder(db.userDao().getAllOrderByName(), config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }
}