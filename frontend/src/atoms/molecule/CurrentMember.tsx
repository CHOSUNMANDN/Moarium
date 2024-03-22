import CheckBox from '../atom/checkbox';

type data = {
  name: string;
  token: string;
  week: { [key: string]: string };
  userId: number;
  setIsMemberInfoOpen: (state: number) => void;
  setIsMemberToken: (state: string) => void;
};

export default function CurrentMember({ name, token, week, userId, setIsMemberInfoOpen, setIsMemberToken }: data) {
  const daysOrder = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'];

  const openMemberInfo = () => {
    window.scrollTo(0, 0);
    setIsMemberInfoOpen(userId);
    setIsMemberToken(token);
  };

  const renderCheckBoxes = () => daysOrder.map((day, idx) => <CheckBox key={idx} day={day} type={week ? week[day] : 'UNDECIDED'} />);

  return (
    <>
      <div className="h-[4rem]" onClick={openMemberInfo}>
        <div className="h-[2rem] font-bold text-[1rem]">{name}</div>
        <div className="flex place-content-between h-[2rem] w-[100%]">
          {renderCheckBoxes()}
          <div className="font-semibold w-[3rem] text-center">{token}</div>
        </div>
      </div>
      <div className="border border-light-grey my-[0.5rem]"></div>
    </>
  );
}
