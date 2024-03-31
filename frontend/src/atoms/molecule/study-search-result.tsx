import { axAuth } from '@/apis/axiosinstance';
import { CHECK } from '@/utils/constans/custumType';
import React, { ChangeEvent, useEffect, useState } from 'react';
import { userToken } from '../../states/index';
import { useRecoilState } from 'recoil';

interface SearchResult {
  clubName: string;
  clubProfileImage: string;
}

interface SearchValueType {
  search: string;
}

export default function StudySearchResult(data: SearchValueType) {
  const [token, setToken] = useRecoilState(userToken);
  const [searchResults, setSearchResults] = useState<SearchResult[]>([]);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/main/search',
      params: { search: data.search }, // name을 파라미터로 사용
    })
      .then(res => {
        const searchResults = res.data.result.searchResults;
        setSearchResults(searchResults);
      })
      .catch(err => {
        console.log(err);
      });
  }, [data.search]);

  return (
    <div>
      {searchResults.length > 0 && (
        <div className="rounded-md bg-white h-[6rem] border-x-[0.5px] border-t-[0.5px] overflow-y-auto">
          {searchResults.map((item, index) => (
            <div className="flex h-[2rem] border-b-[0.5px]" key={index}>
              <div className="flex justify-center items-center w-[10%]">
                <img className="h-[1.5rem] w-[1.5rem] rounded" src={'data:image/png;base64,' + item.clubProfileImage} alt={`${item.clubName} Image`} />
              </div>
              <div className="flex justify-center items-center font-bold opacity-60 text-[0.7rem] w-[90%]">{item.clubName}</div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
