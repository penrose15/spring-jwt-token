# spring-jwt-token

1. 권한 설계

1-a. Member
* Member는 id, username, password, role 로 구성
* Member와 Role은 다대다 관계(N : N)로 설정
  * 원래는 일대다(1:N) 관계로 설정하려 했으나 구글링 결과 대다수가 다대다 관계로 설정해서 일단 다대다 관계로 설정
  -> 추후 일대다로 변경 예정

1-b. Role
* Role은 User, Manager, Admin으로 역할 설정
* Role은 Privilege를 담는 컨테이너 역할을 수행하므로 하나의 Role은 여러개의 Privilege를 담을 수 있다.
* Role과 Privilige는 일대다 관계(1:N)


1-c. Privilege
* 시스템에서 관리하는 권한 정보를 저장하는 entity 객체
* Role이 User이면 COMMUNICATION_AUTHORITY권한만, Manager이면 COMUNICATION_AUTHORITY, WORK_AUTHORITY 두가지 권한, Admin은 COMUNICATION_AUTHORITY, WORK_AUTHORITY, USER_AUTHORITY 세가지
의 권한을 가진다.
* Privilege와 Role은 다대일(N : 1)관계

2. UserDetails, UserDetailsService
* Authorities에서 Role과 Privilege의 정보를 모두 담기 위해 커스텀

3. MemberService
* create(), findUser() 구현

