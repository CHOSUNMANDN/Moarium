'use client';
import Header from '../atoms/molecule/Header';
import MainCarousel from '../atoms/template/main-carousel';
import { useEffect } from 'react';
import { userToken } from '../states/index';
import { useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';
import { replaceRouterMain } from '@/utils/RouteHandling';
import { hasToken } from '@/utils/validate/ExistenceChecker';

export default function Home() {
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();

  useEffect(() => {
    // 토큰이 있을시 메인페이지 이동
    if (hasToken(token)) {
      replaceRouterMain(router);
    }
  }, []);

  return (
    <>
      <header>
        <Header isVisible={true} />
      </header>
      <main>
        <article>
          <MainCarousel />
        </article>
      </main>
    </>
  );
}
