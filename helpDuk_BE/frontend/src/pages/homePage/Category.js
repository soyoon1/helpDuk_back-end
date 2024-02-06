import './Category.css';
import React, { useState, useEffect } from 'react';

const Category = (props) => {
  const [visibilityAnimation, setVisibilityAnimation] = useState(false);
  const [repeat, setRepeat] = useState(null);

  const [categoryDtoList, setCategoryDtoList] = useState({
    onlyYet: false,
    onlyMine: false,
    school: false,
    dormitory: false,
    etc: false,
    print: false,
    food: false,
    coverFor: false,
    clean: false,
    eventAssistant: false,
    bug: false,
  });

  useEffect(() => {
    if (props.visibility) {
      clearTimeout(repeat);
      setRepeat(null);
      setVisibilityAnimation(true);
    } else {
      setRepeat(setTimeout(() => {
        setVisibilityAnimation(false);
      }, 400));
    }
  }, [props.visibility]);

  const handleCheckboxChange = (itemName) => {
    setCategoryDtoList({
      ...categoryDtoList,
      [itemName]: !categoryDtoList[itemName],
    });

    // 부모 컴포넌트로 선택된 카테고리 정보 전달
    props.onCategoryChange(categoryDtoList);
  };

  return (
    <article className={`category-dropdown ${props.visibility ? 'slide-fade-in-dropdown' : 'slide-fade-out-dropdown'}`}>
      {visibilityAnimation && (
        <ul>
          <br />  

          <li>
            <h4>골라보기</h4>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.onlyYet}
              onChange={() => handleCheckboxChange('onlyYet')}
            />
            <label>거래가능글만</label>
          </li>  
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.onlyMine}
              onChange={() => handleCheckboxChange('onlyMine')}
            />
            <label>내 의뢰글만</label>
          </li>
          <br />

          <li>
            <h4>위치</h4>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.school}
              onChange={() => handleCheckboxChange('school')}
            />
            <label>학교 안</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.dormitory}
              onChange={() => handleCheckboxChange('dormitory')}
            />
            <label>기숙사</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.etc}
              onChange={() => handleCheckboxChange('etc')}
            />
            <label>기타</label>
          </li>
          <br />

          <li>
            <h4>세부 사항</h4>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.print}
              onChange={() => handleCheckboxChange('print')}
            />
            <label>프린트</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.food}
              onChange={() => handleCheckboxChange('food')}
            />
            <label>음식</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.coverFor}
              onChange={() => handleCheckboxChange('coverFor')}
            />
            <label>알바 대타</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.clean}
              onChange={() => handleCheckboxChange('clean')}
            />
            <label>청소</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.eventAssistant}
              onChange={() => handleCheckboxChange('eventAssistant')}
            />
            <label>행사 보조</label>
          </li>
          <li>
            <input
              type="checkbox"
              checked={categoryDtoList.bug}
              onChange={() => handleCheckboxChange('bug')}
            />
            <label>벌레</label>
          </li>
          <br />
        </ul>
      )}
    </article>
  );
};

export default Category;
