# ScreenScenes

## :clipboard: 목차

- :books: <a href="#outline">개요</a>
- :wrench: <a href="#tech">기술 스택</a>
- :scroll: <a href="#erd">ERD(Entity-Relation Diagram)</a>
- :family: <a href="#team">팀원 역할 소개</a>
- :bookmark_tabs: <a href="#function">기능</a>
- :mag_right: <a href="#fullfill">보완할점</a>

# :books: <a name="outline">개요</a>
<br/>
<img src="https://github.com/Psh230412/FirstWeb/assets/134483516/d47f4916-dc44-4d24-ad16-1a850bb14f0a"/>

> **프로젝트** : 여행 경로 탐색
>
> **프로젝트 이름** : ScreenScenes
>
> **분류** : 팀 프로젝트
>
> **제작 기간** : 2023.08.01 ~ 2023.08.16
> 
> **주 사용자층** : 본인이 좋아하는 영화의 촬영지를 방문하고 싶어하는 사람들
>
> **프로젝트 목표** : 영화의 촬영지를 선택하면 여행 경로를 추천해주는 웹 사이트

<br/>

# :scroll: <a name="function">기능</a>


- <a href="#fun0">홈화면
- <a href="#fun1">1.&nbsp;로그인</a>
 	- 1-1. 회원가입
- <a href="#fun2">2.&nbsp;영화 선택</a>
   	- 2-1. 영화 선택
- <a href="#fun3">3.&nbsp;영화 촬영지 선택</a>
  	- 3-1. 영화 촬영지 선택
- <a href="#fun4">4.&nbsp;경로 선택</a>
 	- 4-1. 경로선택
- <a href="#fun5">5.&nbsp;마이페이지</a>
	- 5-1. 경로 확인
   	- 5-2. 촬영지 주변 호텔 & 레스토랑 확인
  	- 5-3. 경로 삭제
- <a href="#fun6">6.&nbsp;내 정보 변경</a>
	- 6-1. 프로필 사진 변경
  	- 6-2. 닉네임 변경
  	- 6-3. 비밀번호 변경

### <a name="fun0">홈화면</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/ed23b7be-cb0f-4e94-bfde-e9642ca454b4"/>

### <a name="fun1">영화 선택</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/f896d3d3-c780-4721-a363-ea858a7b9903"/>

### <a name="fun2">영화 촬영지 선택</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/59a05239-ebf3-48f9-ad63-27f231c9baa0"/>

### <a name="fun3">경로 선택</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/7aaf0654-aba7-4c2b-a0d6-0db8c4a68915"/>

#### 경로는 어떻게 짜여지는가? </br>
첫 번째 경로: 선택환 촬영지 4개의 위도와 경도 정보를 이용하여 각 촬영지별 거리를 계산, 최단 거리를 제공 </br>
두 번째 경로, 세 번째 경로: 앞 번째 경로를 제외한 나머지 촬영지 중에서 랜덤으로 하나를 선택한 뒤, 최단 거리에 있는 촬영지 중 거리가 100m 이상인 촬영지 4개로 구성 
</br></br>

#### - 사용자에게 영화 선택 → 촬영지 선택 → 경로 선택의 순서대로 정보 제공 및 선택을 할 때, 정보를 어떻게 관리할 것인가? </br>
사용자가 각 단계에서 선택한 정보를 세션에 임시 저장. </br>
이를 통해 사용자는 이전 단계로 돌아가는 경우 선택한 정보를 유지할 수 있도록 하고, 페이지를 이탈했을 경우 불필요한 정보가 DB에 저장되는 것을 방지</br>
최종 경로까지 선택을 완료하면 세션에 저장하고 있었던 정보들을 DB에 저장</br>

</br></br>

#### <a>촬영지 주변 호텔 & 레스토랑 확인</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/8c7b4976-2d84-4dba-85d3-f7ae4d7b6fd5"/>

### <a name="fun4">마이페이지</a>
<img src="https://github.com/Psh230412/MovieTravelProject/assets/134483516/32a21a8c-319f-4f8c-b39d-ceaee78a29f7"/>

### <a name="fun5">내 정보 변경</a>
<img src="https://github.com/Psh230412/0623Start/assets/134483516/14b420c1-7f55-447d-a054-d7e4d51c074a"/>



# :wrench: <a name="tech">기술 스택</a>

<h4>데이터베이스</h4>
<div align="left">
   <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
</div> 
<h4>백엔드</h4>
<div align="left">
    <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=Java&logoColor=white"/>
    <img src="https://img.shields.io/badge/Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"/>
    <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</div>
</div> 
<h4>프론트엔드</h4>
<div align="left">
   <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"/>
   <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"/>
   <img src="https://img.shields.io/badge/JAVASCRIPT-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"/>
</div>
<h4>API</h4>
<div align="left">
   <img src="https://img.shields.io/badge/Google%20Maps-4285F4?style=for-the-badge&logo=googlemaps&logoColor=white" />
   <img src="https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white" />
   <img src="https://img.shields.io/badge/GEOCODING-00874D?style=for-the-badge&logo=geocaching&logoColor=white" />
 <img src="https://img.shields.io/badge/jsoup-0085F9?style=for-the-badge&logo=maildotru&logoColor=white" />
   <img src="https://img.shields.io/badge/commons%20fileupload-005FF9?style=for-the-badge&logo=maildotru&logoColor=white" />
	
</div>
<h4>협업도구</h4>
<div align="left">
   <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white" />
   <img src="https://img.shields.io/badge/FIGMA-F24E1E?style=for-the-badge&logo=figma&logoColor=white" />
</div>

# :scroll: <a name="erd">ERD</a>

## 초기 ERD

<img src="https://github.com/Psh230412/FirstWeb/assets/134483516/3b352032-c2c4-4e8b-b76b-4f2a3e6e805a" width="100%"/>

## 현재 ERD

<img src="https://github.com/Psh230412/FirstWeb/assets/134483516/71ce82e5-07df-423d-9f2f-30ee45f48631" width="100%"/>

### 변경이유

대부분의 데이터를 request와 session으로 이동시켜서 초기 구상 시 보다 간소화할 수 있었음

# :family: <a name="team">팀원 역할 소개</a>
<br/>

| 이름 | 박상현 | 김명완 | 선보라 | 성지수 | 이강현 | 이정빈
| :---: | :----------: | :----------: | :----------: | :----------: | :----------: | :----------: |
| 역할 | 팀장 </br> 지도제작 </br> 기획 </br> 영화선택 | 경로 계산 </br> 알고리즘 </br> 크롤링 | HTML / CSS </br> 레이아웃 </br> 총괄 | 경로선택 </br> 마이페이지 경로 | 촬영지 선택 </br> 호텔 / 식당 지도 | 회원가입 </br> 마이페이지 |



# :mag_right: <a name="#fullfill">보완할점</a>
### 1. 경로 테이블의 확장성을 고려하지 않아서 수정이 필요
### 2. 경로 선택 알고리즘 개선
### 3. 방대한 영화 개수로 검색 기능의 추가가 필요
### 4. 대중교통 정보 반영
