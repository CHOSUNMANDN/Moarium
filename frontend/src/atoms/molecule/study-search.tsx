import { axAuth } from '@/apis/axiosinstance';
import { CHECK } from '@/utils/constans/custumType';
import React, { ChangeEvent, useEffect, useState } from 'react';
import { userToken } from '../../states/index';
import { useRecoilState } from 'recoil';
import StudySearchResult from './study-search-result';

interface SearchValueType {
  search: string;
}

export default function StudySearch() {
  const [token, setToken] = useRecoilState(userToken);
  const [searchValue, setSearchValue] = useState<SearchValueType>({ search: '' });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setSearchValue(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <div className="w-[80%]">
      <div className="h-[6rem]">
        <StudySearchResult search={searchValue.search} />
      </div>
      <div className="h-[2rem] border-[0.5px] rounded-md opacity-60">
        <input name="search" className="w-[100%] h-[100%] rounded-md text-center" type="text" onChange={handleChange} placeholder="스터디 검색" />
      </div>
    </div>
  );
}
