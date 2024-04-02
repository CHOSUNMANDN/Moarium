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
import FavoritesClubInformationList from '@/atoms/molecule/favorites-club-information-list';
import StudySearch from '@/atoms/molecule/study-search';
import NavigationFooter from '@/atoms/molecule/navigation-footer';

interface ClubFavoriteInformationType {
  clubId: number;
  clubLeaderName: string;
  clubName: string;
  clubProfileImage: string;
  favoriteCheck: string;
  numberMember: string;
}

export default function Login() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [clubFavoriteInformations, setClubFavoriteInformations] = useState<ClubFavoriteInformationType[]>([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);

  // TODO: 무한 로딩 이유?
  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/clubs/informations',
    })
      .then(res => {
        const clubFindInformations = res.data.result.clubFindInformations;
        setClubFavoriteInformations(clubFindInformations);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  return (
    <>
      <header>
        <Header isVisible={false} />
      </header>
      <main>
        <article>
          <div>
            <FavoritesClubInformationList data={clubFavoriteInformations} subTitle={`소속된 동아리가 없습니다.`} />
          </div>
          <div className="flex flex-col justify-center  items-center ">
            <StudySearch />
          </div>
        </article>
      </main>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </>
  );
}
