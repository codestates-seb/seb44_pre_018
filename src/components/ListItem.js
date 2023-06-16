import ItemView from './global/ItemView';
import ItemAnswer from './global/ItemAnswer';
import TagList from './global/TagList';
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

const ListItem = () => {
  return (
    <Item className="transition flex items-center justify-between border-b-[1px] border-black/[.3] border-solid cursor-pointer py-3">
      <div className="w-4/5">
        <h3 className="itemTitle text-base ">
          <span>How to generate a key when you know how to check the key?</span>
        </h3>
        <p className="text-sm font-light py-2">
          I am trying to use math kernel library of intel along with the intel
          fortran compiler, which comes built into the oneAPI basekit. However,
          I am not able to use ifort since the terminal complains ...
        </p>
        <TagList />
      </div>
      <div className="w-1/5 text-right">
        <ItemView />
        <ItemAnswer />
      </div>
    </Item>
  );
};

export default ListItem;
