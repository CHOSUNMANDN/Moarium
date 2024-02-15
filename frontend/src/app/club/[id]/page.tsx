'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../../atoms/molecule/header';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import { useRouter } from 'next/navigation';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import NavigationFooter from '@/atoms/molecule/navigation-footer';

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[2rem]">
          <div className={`text-center font-bold text-[1.5rem] ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원
          </div>
          <div className={`text-center font-bold text-[1.5rem] ${type === 1 ? 'text-black' : 'text-grey'}`} onClick={() => setType(1)}>
            휴먼 인원
          </div>
          <div className={`border-t-2 mt-[1rem] ${type === 0 ? 'border-black' : 'border-grey'}`}></div>
          <div className={`border-t-2 mt-[1rem] ${type === 1 ? 'border-black' : 'border-light-grey'}`}></div>
        </div>
      </section>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
