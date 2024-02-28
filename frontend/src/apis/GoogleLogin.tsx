import {GoogleOAuthProvider} from "@react-oauth/google";
import GoogleLoginBtn from "@/atoms/atom/GoogleLoginButton";

const GoogleLoginButton = () => {
  // client id 입력!!
  const clientId = '';
  return (
      <>
          <GoogleOAuthProvider clientId={clientId}>
            <GoogleLoginBtn></GoogleLoginBtn>
          </GoogleOAuthProvider>
      </>
  );
};

export default GoogleLoginButton;