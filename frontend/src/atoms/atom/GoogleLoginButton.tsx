import { useGoogleLogin } from '@react-oauth/google';
import { axBase } from '../../apis/axiosinstance';
export default function GoogleLoginButton() {
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
      .then(() => {
        console.log("로그인 성공 ㄴㅇㅅ");
      })
      .catch(err => {
        console.log(err);
      });
  }
  return (
    <button className="bg-[url('/google-login.svg')] bg-auto bg-no-repeat w-[179px] h-[40px]" onClick={login}></button>
  );
}

