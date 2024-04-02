import { useGoogleLogin } from '@react-oauth/google';
import { axBase } from '../../apis/axiosinstance';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { MEMBER, NON_MEMBER } from '@/utils/constans/custumType';
import { replaceRouterFindEmail, replaceRouterFirstLogin, replaceRouterMain } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';

interface LoginResultType {
  accessToken: string;
  memberStatus: string;
}

export default function GoogleLoginButton() {
  const [loginResult, setLoginResult] = useState<LoginResultType>({ accessToken: '', memberStatus: '' });
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();
  const login = useGoogleLogin({
    onSuccess: codeResponse => doGoogleLogin(codeResponse.code),
    flow: 'auth-code',
  });
  const doGoogleLogin = (authCode: String) => {
    axBase()({
      method: 'post',
      url: '/users/login/google',
      data: {
        authCode: authCode,
      },
    })
      .then(res => {
        console.log('로그인 성공 ㄴㅇㅅ');
        const data = res.data.result;
        setLoginResult(data);
        console.log(data);

        if (loginResult.memberStatus == MEMBER) {
          replaceRouterMain(router);
          setToken(loginResult.accessToken);
        }
        if (loginResult.memberStatus == NON_MEMBER) {
          replaceRouterFirstLogin(router);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };
  return <button className="bg-[url('/google-login.svg')] bg-auto bg-no-repeat w-[179px] h-[40px]" onClick={login}></button>;
}
