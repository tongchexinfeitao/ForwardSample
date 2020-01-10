package com.bw.forwardsample.contract;

import com.bw.forwardsample.model.bean.LoginBean;
import com.bw.forwardsample.model.bean.RegisterBean;

public interface IRegisterAndLoginContract {
    interface IView {

        void onRegisterSuccess(RegisterBean registerBean);

        void onRegisterFailure(Throwable throwable);

        void onLoginSuccess(LoginBean loginBean);

        void onLoginFailure(Throwable throwable);
    }

    interface IPresenter {
        void register(String phone, String pwd);

        void login(String phone, String pwd);
    }

    interface IModel {
        void register(String phone, String pwd, IModelCallback iModelCallback);

        void login(String phone, String pwd, IModelCallback iModelCallback);

        interface IModelCallback {

            void onRegisterSuccess(RegisterBean registerBean);

            void onRegisterFailure(Throwable throwable);

            void onLoginSuccess(LoginBean loginBean);

            void onLoginFailure(Throwable throwable);
        }
    }
}
