const Filter = () => {
  return (
    <ul className="flex my-8">
      <li className="on pointBu01 text-xs px-4 py-2 mr-2">
        <button>최신순</button>
      </li>
      <li className="pointBu01 text-xs px-4 py-2 mr-2">
        <button>조회 순</button>
      </li>
      <li className="pointBu01 text-xs px-4 py-2">
        <button>최근 답변 달린 순</button>
      </li>
    </ul>
  );
};

export default Filter;
