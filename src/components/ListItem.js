import Tag from './global/Tag';
import ItemView from './global/ItemView';
import ItemAnswer from './global/ItemAnswer';

const ListItem = () => {
  return (
    <li>
      <div>
        <h4>How to generate a key when you know how to check the key?</h4>
        <p>
          I am trying to use math kernel library of intel along with the intel
          fortran compiler, which comes built into the oneAPI basekit. However,
          I am not able to use ifort since the terminal complains ...
        </p>
        <Tag />
      </div>
      <div>
        <ItemView />
        <ItemAnswer />
      </div>
    </li>
  );
};

export default ListItem;
