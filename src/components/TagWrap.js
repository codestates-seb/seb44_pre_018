import styled from 'styled-components';

const TagBox = styled.ul`
  display: grid;
  grid-gap: 10px;
  gap: 10px;
  grid-template-columns: repeat(4, 1fr);
  margin: 20px 0;
  > li {
    transition: all 0.3s;
    border: 1.5px solid rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    padding: 8px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: start;
    cursor: pointer;
    &:hover {
      border: 1.5px solid rgba(43, 102, 171, 1);
      > a {
        transform: rotate(-3deg);
      }
      span {
        color: rgba(0, 0, 0, 0.5);
      }
    }
    > a {
      transition: all 0.3s;
      font-size: 11px;
      padding: 5px 10px;
      border-radius: 5px;
      color: rgba(43, 102, 171, 1);
      font-weight: 300;
    }
    > p {
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 4;
      -webkit-box-orient: vertical;
      font-size: 12px;
      font-weight: 300;
      margin: 15px 0;
      line-height: 16px;
    }
    > ol {
      width: 100%;
      display: flex;
      justify-content: space-between;
      span {
        transition: all 0.3s;
        display: block;
        font-size: 11px;
        color: rgba(0, 0, 0, 0.3);
      }
    }
  }
`;

const TagWrap = () => {
  return (
    <TagBox>
      <li>
        <a className="pointBu01" href="!#">
          javascript
        </a>
        <p>
          For questions about programming inECMAScript (JavaScript/Js) and
          itsdifferent dialects/implementations(except for ActionScript). Note
          that...
        </p>
        <ol>
          <li>
            <span>2499774</span>
            <span>questions</span>
          </li>
          <li>
            <span>347 asked today</span>
            <span>2205 this week</span>
          </li>
        </ol>
      </li>
    </TagBox>
  );
};

export default TagWrap;
