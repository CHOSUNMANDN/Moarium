import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const sessionStorage = typeof window !== 'undefined' ? window.sessionStorage : undefined;

const { persistAtom } = recoilPersist({
  key: 'sessionStorage',
  storage: sessionStorage,
});

export const userToken = atom({
  key: 'userToken',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const myClubMemberId = atom({
  key: 'userMemberId',
  default: 0,
  effects_UNSTABLE: [persistAtom],
});

export const myClubGrade = atom({
  key: 'iNuriKing',
  default: '',
  effects_UNSTABLE: [persistAtom],
});
