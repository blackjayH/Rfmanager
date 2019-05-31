# team project : Rfmanager(18.3~6) 10주 4People
프로젝트 설명
- 4인 팀 + 자유주제 + 캡스톤 프로젝트
- Barcode(바코드) + STT(음성)로 인식하는 냉장고 유통기한 관리시스템
- 쇼핑리스트 + 냉장고 + 상품등록 3개로 구성
- QR코드 인식 >> STT(음성) 인식 변경 >> QR코드의 비효율성과 한글 인식률의 낮음 
- Raspberry Pi + 우분투(Apache + PHP + MySQL) APMSetup으로 웹 서버 구축
- 웹 서버 MySQL에 유저의 데이터 저장(쇼핑리스트 데이터 + 냉장고상품리스트 데이터 백업) 
- 웹 서버 PHP를 통해 카카오봇 기능(안드로이드 앱을 켜지 않고 카카오 플러스친구를 통해 쇼핑리스트와 냉장고상품리스트 확인)
- 쇼핑리스트 : 마트에 갈 때 장보기 할 품목을 넣어 논 리스트 
- 냉장고(Main) : 각 조건에 따른 분류 및 정렬 / Notification / 만개의 리스트 웹사이트를 통해 레시피 검색
- GridView의 메인을 StickyGridHeader 라이브러리로 변경
- 상품등록 : Barcode + 음성 인식을 통해 상품 등록

나의 담당부분 냉장고 유통기한 관리시스템 안드로이드 앱 ver2. 
- 쇼핑리스트 : ListView 
- 냉장고(Main) : GridView 
- 상품등록(Barcode 입력) : Zxing + Jsoup(beepscan 웹의 유통기한 정보를 파싱)
- 냉장고상품리스트 + 쇼핑리스트 웹 DB에 백업 : Android + SQLite + PHP + MySQL 
- 카카오플러스 친구API를 이용한 간단한 카카오 봇
- 유저 로그인 기능 : Android + PHP + MySQL 
Apache HTTP Client >> RequestHttpURLConnection + AsyncTask(비동기식 처리)로 변경

