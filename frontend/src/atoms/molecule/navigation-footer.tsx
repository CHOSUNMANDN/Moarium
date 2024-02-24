import React from 'react';
import Navigation from '../template/navigation';

type data = {
  isMyClubGrade: string;
};

export default function NavigationFooter({ isMyClubGrade }: data) {
  return (
    <footer className="mt-[10rem]">
      <Navigation now={1} isLeader={isMyClubGrade} />
    </footer>
  );
}
