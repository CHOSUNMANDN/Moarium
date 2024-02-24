type data = {
  text: string;
  addClass?: string;
};

export default function SmallButton({ text, addClass }: data) {
  return <button className={`w-[5.19rem] h-[1.63rem] bg-blue rounded-[0.63rem] text-white font-bold ${addClass}`}>{text}</button>;
}
