import { axAuth } from '@/apis/axiosinstance';
import { CHECK } from '@/utils/constans/custumType';
import React, { ChangeEvent, useState } from 'react';
import { userToken } from '../../states/index';
import { useRecoilState } from 'recoil';

interface FavoritesClubInformationType {
  clubId: number;
  clubLeaderName: string;
  clubName: string;
  clubProfileImage: string;
  favoriteCheck: string;
  numberMember: string;
}

export default function FavoritesClubInformation(data: FavoritesClubInformationType) {
  const [token, setToken] = useRecoilState(userToken);

  const favoriteCheckButtonHandler = () => {
    axAuth(token)({
      method: 'post',
      url: `/clubs/${data.clubId}/favorites/check`,
    })
      .then(res => {})
      .catch(err => {});
  };

  return (
    <div className="flex w-[15rem] h-[4.5rem] items-center justify-evenly mb-[.8rem] border-[.1px] rounded">
      <div>
        <img className="h-[2.5rem] w-[2.5rem] rounded" src={'data:image/png;base64,' + data.clubProfileImage} alt={`${data.clubName} Image `} />
      </div>
      <div>
        <div className="text-[0.8rem] font-bold ">{data.clubName}</div>
        <span className="text-[0.6rem] text-[#959595]">
          <div>{data.clubLeaderName}</div>
          <div>현재인원 : {data.numberMember}명</div>
        </span>
      </div>
      <div className="h-[1.5rem] w-[1.5rem]" onClick={favoriteCheckButtonHandler}>
        {data.favoriteCheck.includes(CHECK) ? <img src="/Images/favorite_star_check.png" /> : <img src="/Images/favorite_star_uncheck.png" />}
      </div>
    </div>
  );
}
