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
  subTitle: string; // subTitle 속성 추가
}

export default function FavoritesClubInformationList(props: FavoritesClubInformationListProps & { subTitle: string }) {
  const { data, subTitle } = props;

  return (
    <div className="flex flex-col h-[23rem]   items-center pt-[1rem] overflow-y-auto">
      {data.length > 0 &&
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
      {data.length == 0 && (
        <>
          <div className="h-[33%] w-[80%]"></div>
          <div className="h-[33%] w-[80%] text-center opacity-70">
            {subTitle.split('\n').map((line, index) => (
              <React.Fragment key={index}>
                {line}
                {index < subTitle.split('\n').length - 1 && <br />} {/* 줄 바꿈 태그 */}
                {index < subTitle.split('\n').length - 1 && <br />} {/* 줄 바꿈 태그 */}
              </React.Fragment>
            ))}
          </div>
          <div className="h-[33%] w-[80%]"></div>
        </>
      )}
    </div>
  );
}
