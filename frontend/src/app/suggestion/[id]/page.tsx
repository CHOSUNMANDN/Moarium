'use client';
import Header from '@/atoms/molecule/header';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import React, { useState, useEffect } from 'react';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';
import { axAuth } from '@/apis/axiosinstance';
import { usePathname } from 'next/navigation';

interface SuggestionDataType {
  check: boolean;
  classification: string;
  id: number;
  title: string;
}

export default function Main() {
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();
  const [suggestionData, setSuggestionData] = useState<SuggestionDataType>();
  const pathName: string = usePathname();
  let id: string | undefined;

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  const pushCheck = (id: number | undefined) => {
    if (isKing) {
      axAuth(token)({
        method: 'post',
        url: '/suggestion/check',
        data: {
          suggestionId: id,
        },
      })
        .then(res => {
          res.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  };

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/suggestion/' + id,
    }).then(res => {
      const data = res.data;
      setSuggestionData(data);
    });
  }, [suggestionData]);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <article className="flex flex-col  m-[1.5rem] ">
        <div className=" rounded w-[95%] h-[95%]">
          <div className="flex justify-between">
            <div>
              <div className="">번호: {suggestionData?.id}</div>
              <div>분류: {suggestionData?.classification}</div>
            </div>
            <div>
              확인
              <input className="w-[1.1rem] mr-[0.2rem]" type="checkbox" checked={suggestionData?.check} onChange={() => pushCheck(suggestionData?.id)} id="myCheckbox" />
            </div>
          </div>
          <div className="font-bold text-lg mt-[0.5rem]">{suggestionData?.title}</div>
        </div>
      </article>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
