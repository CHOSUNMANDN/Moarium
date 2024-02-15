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
  const [isKing, setIsKing] = useRecoilState(isNuriKing);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>

      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
