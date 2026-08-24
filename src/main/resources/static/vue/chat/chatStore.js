const {defineStore}=Pinia
const {nextTick}=Vue
// nextTick => DOM업데이트 => 다음 문장을 실행하고 싶은 경우
const useChatStore=defineStore('chat',{
	state:()=>({
		stomp:null,
		users:[], // 접속자 목록
		messages:[], // 화면출력 채팅 내용
		publicMessages:[], // 전체채팅 메세지
		privateMessages:[], // 1:1채팅 메세지
		currentRoom:'public', // 현재 채팅방
		loginUser:'', // 로그인 사용자
		chatBodyEl:null, // 채팅창 DOM
		msg:'' // 입력 메세지
	}),
	// kim, hong => kim_hong
	actions:{
		// 채팅방 ID 생성
		makeRoomId(user1,user2){
			return [
				user1,
				user2
			]
			.sort()
			.join('_')
		},
		// 채팅방 변경
		changeRoom(user){
			// 전체 채팅
			if(user==='public'){
				this.currentRoom='public'
				this.messages=this.publicMessages
			}
			// 1:1 채팅
			else{
				const roomId=this.makeRoomId(this.loginUser,user)
				// 해당방이 없는 경우
				if(!this.privateMessages[roomId]){
					this.privateMessages[roomId]=[]
				}
				this.messages=thi.privateMessages[roomId]
			}
			this.scrollToBottom()
		},
		async scrollToBottom(){
			await nextTick
			if(this.chatBodyEl){
				this.chatBodyEl.scrollTop=
					this.chatBodyEl.scrollHeight
			}
		},
		// 서버연결 (WebSocket 연동)
		connect(){
			const socket=new SockJS('/chat-ws')
			// stomp 연결
			this.stomp=Stomp.over(socket)
			// => stomp에 대한 메모리 할당
			this.stomp.connect({},()=>{
				console.log("WebSocket 연결 성공")
				// 접속자 목록 가지고오기
				this.stomp.subscribe(
					'/topic/users',
					msg=>{
						const users=JSON.parse(msg.body)
						// 본인은 제외
						this.users=users.filter(u=>u!==this.loginUser)
					}
				)
			})
		}
	}
})