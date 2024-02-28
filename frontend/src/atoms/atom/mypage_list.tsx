type data = {
  text: string;
  addClass?: string;
};

export default function MyPageButton({ text, addClass }: data) {
  return <button className={`w-[100%] h-[3.3rem] bg-lightgrey text-black font-regular border-blue border-t-2 text-left ${addClass}`}>{text}  
  </button>;
}
