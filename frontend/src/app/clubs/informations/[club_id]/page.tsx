'use client';
import React, { useState, useEffect } from 'react';
import Link from 'next/link';
import { useRecoilState } from 'recoil';
import { useRouter } from 'next/navigation';
import { hasToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterFindEmail, replaceRouterFindPassword, replaceRouterMain } from '@/utils/RouteHandling';
import { isNuriKing, userToken } from '@/states';
import { axAuth, axBase } from '@/apis/axiosinstance';
import Header from '@/atoms/molecule/header';
import MainCarousel from '@/atoms/template/main-carousel';

export default function Login() {
  const router = useRouter();

  return (
    <>
      <header>
        <Header isVisible={false} />
      </header>
      <main>
        <article>
          <MainCarousel />
        </article>
      </main>
    </>
  );
}
