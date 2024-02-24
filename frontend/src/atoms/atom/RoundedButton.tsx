type data = {
  text: string;
  addClass?: string;
};

export default function SmallButton({ text, addClass }: data) {
  return <button className={`w-[7.31rem] h-[2.06rem] bg-blue rounded-[0.63rem] text-white font-bold ${addClass}`}>{text}</button>;
}
