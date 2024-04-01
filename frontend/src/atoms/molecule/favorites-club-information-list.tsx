import { axAuth } from '@/apis/axiosinstance';
import { CHECK } from '@/utils/constans/custumType';
import React, { ChangeEvent, useEffect, useState } from 'react';
import { userToken } from '../../states/index';
import { useRecoilState } from 'recoil';
import FavoritesClubInformation from './favorites-club-information';

interface ClubFavoriteInformationType {
  clubId: number;
  clubLeaderName: string;
  clubName: string;
  clubProfileImage: string;
  favoriteCheck: string;
  numberMember: string;
}

interface FavoritesClubInformationListProps {
  data: ClubFavoriteInformationType[]; // 배열을 포함하는 객체로 props 정의
}

export default function FavoritesClubInformationList(props: FavoritesClubInformationListProps) {
  const { data } = props;

  return (
    <div className="flex flex-col h-[16.8rem] items-center pt-[1rem] overflow-y-auto">
      {data &&
        data.map((item, index) => (
          <div key={index}>
            <FavoritesClubInformation
              clubId={item.clubId}
              clubLeaderName={item.clubLeaderName}
              clubName={item.clubName}
              clubProfileImage={item.clubProfileImage}
              favoriteCheck={item.favoriteCheck}
              numberMember={item.numberMember}
            />
          </div>
        ))}
    </div>
  );
}
