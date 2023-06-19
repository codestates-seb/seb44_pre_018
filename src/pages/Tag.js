import SearchBar from 'components/SearchBar';
import TagItem from 'components/TagWrap';
import Pagination from 'components/global/Pagination';

const Tag = () => {
  return (
    <div className="inner">
      <h3 className="maintitle">Tags</h3>
      <p className="my-6 font-light break-keep text-base">
        A tag is a keyword or label that categorizes your quesion with other,
        similar questions. <br />
        Using the right tags makes it easier for others to find and answer your
        question.
      </p>
      <SearchBar />
      <TagItem />
      <Pagination />
    </div>
  );
};

export default Tag;
