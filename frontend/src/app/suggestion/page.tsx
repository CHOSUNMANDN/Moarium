'use client';
import Header from '@/atoms/molecule/header';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import React, { useState, useEffect } from 'react';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import { useRouter } from 'next/navigation';
import { axAuth } from '@/apis/axiosinstance';
import SuggestionArticle from '@/atoms/molecule/suggestion-article';
import Button from '../../atoms/atom/middle-button';
import SuggestionAltertModal from '@/atoms/atom/suggestion-altert-modal';
import { goToPageTop } from '@/utils/windowScrollUtils';

interface suggestionDataPropsTypeOne {
  id: number;
  classification: string;
  title: string;
  holidayPeriod: string;
  check: boolean;
}

export default function Main() {
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();
  const [suggestionLists, setSuggestionLists] = useState([]);
  const [isSuggestionWriteModalOpen, setSuggestionWriteModalOpen] = useState(false);

  const font = `text-white font-bold`;
  const justify = `flex justify-center`;
  const numberCss = `w-[20%] flex ${justify} `;
  const classificationCss = `w-[18%] ${justify} `;
  const titleCss = `w-[72%] ${justify} `;

  const numberFirstCss = `${numberCss} ${font}`;
  const classificationFirstCss = `${classificationCss} ${font}`;
  const titleFirstCss = `${titleCss} ${font}`;

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
      url: '/suggestion',
    }).then(res => {
      const data = res.data;
      setSuggestionLists(data.suggestionLists);
    });
  }, [suggestionLists]);

  const pushSuggestionWrite = () => {
    goToPageTop();
    setSuggestionWriteModalOpen(true);
  };

  return (
    <main>
      {isSuggestionWriteModalOpen ? <SuggestionAltertModal setSuggestionWriteModalOpen={setSuggestionWriteModalOpen} /> : null}
      <header>
        <Header isVisible={false} />
      </header>
      <article className="flex flex-col items-center ">
        <p className="font-bold text-3xl ml-[0.5rem] mt-[2rem] mb-[1rem]">건의 게시판</p>
        <div className=" w-[95%]">
          <div className="flex justify-center">
            <div className={`${numberFirstCss} rounded-tl-[0.63rem] bg-blue`}>번호</div>
            <div className={`${classificationFirstCss} bg-blue`}>분류</div>
            <div className={`${titleFirstCss} rounded-tr-[0.63rem] bg-blue`}>제목</div>
          </div>
          <div>
            {suggestionLists
              .slice()
              .reverse()
              .map((item: suggestionDataPropsTypeOne, idx) => (
                <div key={idx} className="flex justify-center border border-blue h-[2rem]">
                  <SuggestionArticle id={item.id} classification={item.classification} title={item.title} check={item.check} />
                </div>
              ))}
          </div>
          <div className="flex  mt-[1rem]" onClick={pushSuggestionWrite}>
            <Button text={'글쓰기'} addClass="text-2xl" />
          </div>
        </div>
      </article>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
