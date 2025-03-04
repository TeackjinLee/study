spring-react-project 시작


signIn (로그인)

- request
{
    * email : String,
    * password : String
}

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
    token : "jwt...",
    expiredDate: 123456789
}

fail 
- 로그인 실패
Http Status - 401 (Unauthorized)
{
    code : "SF",
    message : "Sign In Failed."
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "SF",
    message: "Sign In Failed."
}

===========================================================

signUp (회원가입)

- request
{
    * email : String,
    * password : String
    * nickname : String
    * telNumber : String,
    * zipCode : String,
    * address : String,
    addressDetail : String,

}

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail

- 필수 정보 미입력 / 이메일 포멧 불일치 / 비밀번호 8자리 미만 / 전화번호 포멧 불일치

- 이메일 중복
Http Status - 400 (Bad Request)
{
    code : "EE",
    message : "Existed Email"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}


===========================================================


weekly3TopList (주간 상위 3 게시물 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    top3List: BoardListItem[]
}

BoardListItem {
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String,
    favoriteCount : int,
    commentCount : int,
    viewCount : int,
    writeDateTime : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail 
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

currentList (최근 게시물 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    currentList: String[]
}

BoardListItem {
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String,
    favoriteCount : int,
    commentCount : int,
    viewCount : int,
    writeDateTime : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail 
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

popularWordList (인기 검색어 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    resultList: String[]
}

BoardListItem {
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String,
    favoriteCount : int,
    commentCount : int,
    viewCount : int,
    writeDateTime : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail 
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

searchList (검색 게시물 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    resultList: String[]
}

BoardListItem {
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String,
    favoriteCount : int,
    commentCount : int,
    viewCount : int,
    writeDateTime : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail 
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

relativeWordList (관련 검색어 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    resultList: String[]
}

BoardListItem {
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String,
    favoriteCount : int,
    commentCount : int,
    viewCount : int,
    writeDateTime : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail 
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

boardDetail (게시물 상세)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    boardSeq : int,
    title : String,
    content : String,
    boardTitmeImage: String[],
    writeDateTime : String,
    writeEmail : String,
    writeNickname : String,
    writeProfileImage : String 
}

fail
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    code: "NB",
    message: "No Existed Board Number"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

favoriteList (좋아요 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    favoriteList : favoriteListItem[]
}

favoriteListItem {
    email : String,
    nickName : String,
    profileImageUrl : String
}

fail
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

putFavorite (좋아요 기능)

- request

{
    boardNumber : int
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

commentList (댓글 리스트)

- request

{
    boardNumber : int
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    commentList: CommentListItem[]
}

CommentListItem {
    email : String,
    nickName : String,
    writeDateTime : String,
    content : String
}

fail
- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

postComment (댓글 쓰기)

- request

{
    content : string
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    code: "NB",
    message: "No Existed Board."
}

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

boardDelete (게시물 삭제)

- request

{
    boardSeq : int
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail
- 존재하지 않는 게시물
Http Status - 400 (Bad Request)
{
    code: "NB",
    message: "No Existed Board."
}

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 권한 없음
Http Status - 403 (Forbidden)
{
    code: "NP",
    message: "No Permission"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

boardWrite (게시물 쓰기)

- request
{
    * title : String,
    * content : String,
    boardImageList : string[]
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 권한 없음
Http Status - 403 (Forbidden)
{
    code: "NP",
    message: "No Permission"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

boardUpdate (게시물 수정)

- request
{
    * title : String,
    * content : String,
    boardImageList : string[]
}

- response

success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

fail

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 권한 없음
Http Status - 403 (Forbidden)
{
    code: "NP",
    message: "No Permission"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

userBoardList (특정 유저 게시물 리스트)

- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    top3List: BoardListItem[]
}

fail

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 권한 없음
Http Status - 403 (Forbidden)
{
    code: "NP",
    message: "No Permission"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

getUser (유저 정보)
- response
success
Http Status - 200 (OK)
{
    code : "SU",
    message : "Success",
    email : string,
    nickname : string,
    profileImage : string
}

fail

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 권한 없음
Http Status - 403 (Forbidden)
{
    code: "NP",
    message: "No Permission"
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================

patchNickName (닉네임 수정)

- request 
{
    nickName : String
}

- response

Http Status - 200 (OK)
{
    code : "SU",
    message : "Success"
}

- 존재하지 않는 유저
Http Status - 400 (Bad Request)
{
    code: "NU",
    message: "No Existed User."
}

- 데이터베이스 에러
Http Status - 500 (Internal Server Error)
{
    code: "DE",
    message: "Database Error."
}

===========================================================





ㅂ
