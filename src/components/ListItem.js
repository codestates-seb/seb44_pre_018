import { Link } from 'react-router-dom';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';
import { styled } from 'styled-components';

const Item = styled.li`
  .itemTitle {
    display: block;
    position: relative;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    span {
      position: relative;

      &::after {
        content: '';
        width: 0;
        height: 12px;
        position: absolute;
        left: 0;
        bottom: -2px;
        background-color: #c2d3ff;
        opacity: 0.4;
        z-index: 0;
        transition: all 0.5s;
        z-index: -1;
      }
    }
  }

  :hover {
    .itemTitle {
      span {
        &::after {
          width: 100%;
        }
      }
    }
  }
`;

const ListItem = ({ value }) => {
  return (
    <Item className="transition border-b-[1px] border-black/[.3] border-solid cursor-pointer py-3">
      <Link
        className="flex items-center justify-between"
        to={`/detail/${value.questionId}`}
      >
        <div className="w-4/5">
          <h3 className="itemTitle text-base ">
            <span>{value.title}</span>
          </h3>
          <p className="text-sm font-light py-2">{value.content}</p>
          <TagList />
        </div>
        <div className="w-1/5 text-right">
          <ItemView />
          <ItemAnswer />
        </div>
      </Link>
    </Item>
  );
};

export default ListItem;
