import CheckBox from '../atom/checkbox';

type data = {
  name: string;
  token: string;
  week: string[];
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
};

export default function CurrentMember({ name, token, week, userId, setIsMemberInfoOpen }: data) {
  const openMemberInfo = () => {
    setIsMemberInfoOpen(userId);
  };

  return (
    <>
      <div className="h-[4rem]" onClick={openMemberInfo}>
        <div className="h-[2rem] font-bold text-[1rem]">{name}</div>
        <div className="flex place-content-between h-[2rem] w-[100%]">
          {week && week.map((item: string, idx) => <CheckBox key={idx} type={item} />)}
          <div className="font-semibold w-[3rem] text-center">{token}</div>
        </div>
      </div>
      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
