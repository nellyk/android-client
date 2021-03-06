package com.mifos.mifosxdroid.online.clientlist;

import com.mifos.api.DataManager;
import com.mifos.mifosxdroid.base.Presenter;
import com.mifos.objects.client.Client;
import com.mifos.objects.client.Page;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rajan Maurya on 6/6/16.
 */
public class ClientListPresenter implements Presenter<ClientListMvpView> {


    private final DataManager mDataManager;
    private Subscription mSubscription;
    private ClientListMvpView mClientListMvpView;

    @Inject
    public ClientListPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ClientListMvpView mvpView) {
        mClientListMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mClientListMvpView = null;
        if (mSubscription != null) mSubscription.unsubscribe();
    }


    public void loadClients() {
        mClientListMvpView.showProgressbar(true);
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = mDataManager.getAllClients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Page<Client>>() {
                    @Override
                    public void onCompleted() {
                        mClientListMvpView.showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mClientListMvpView.showProgressbar(false);
                        mClientListMvpView.showErrorFetchingClients(
                                "\"There was some error fetching list.\"");
                    }

                    @Override
                    public void onNext(Page<Client> clientPage) {
                        mClientListMvpView.showProgressbar(false);
                        mClientListMvpView.showClientList(clientPage);
                    }
                });
    }

    public void loadMoreClients(int offset, int limit) {
        mClientListMvpView.showProgressbar(true);
        if (mSubscription != null) mSubscription.unsubscribe();
        mSubscription = mDataManager.getAllClients(offset, limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Page<Client>>() {
                    @Override
                    public void onCompleted() {
                        mClientListMvpView.showProgressbar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mClientListMvpView.showProgressbar(false);
                        mClientListMvpView.showErrorFetchingClients(
                                "\"There was some error fetching list.\"");

                    }

                    @Override
                    public void onNext(Page<Client> clientPage) {
                        mClientListMvpView.showProgressbar(false);
                        mClientListMvpView.showMoreClientsList(clientPage);
                    }
                });
    }
}
