const {defineStore} = Pinia
/*
	stomp => controller 
			 @MessageMapping()
	user => kafka => stomp => controller
			 | => application.yml => 환경설정
			 | => docker-compose.yml => 구동
*/
const useBoardStore=defineStore('board_comment',{
	// 여기에 있는 데이터가 변경이 되면 자동으로 HTML이 변경된다
	// 예전 data(){} 와 동일
	state:()=>({
		list:[],
		curpage:1,
		totalpage:0,
		board_no:0,
		sessionId:'',
		count:0,
		msg:'',
		stomp:null, // 알림
		updateMsg:{},
		updateReplyNo:null,
		replyMsg:{},
		replyNo:null, // reReplyNo:null
		reReplyNo:null
	}),
	//getters:{} => computed:{}
	// => 예전 methods:{} 와 동일
	actions:{
		connect(id){
			const sock=new SockJS("/chat-ws")
			this.stomp=Stomp.over(sock)
			this.stomp.connect({},()=>{
				this.stomp.subscribe('/sub/notice/'+id,msg=>{
					this.showToast(msg.body)
					//this.commboardCommentListData(this.curpage,this.board_no)
				})
			})
		},
		disConnection(){
			if(this.stomp&&this.stomp.connected){
				this.stomp.disconnection(()=>{
					console.log('STOMP 종료')
				})
			}
		},
		setCommentData(res) {
			console.log(res.data)
			this.list=res.data.list
			this.curpage=res.data.curpage
			this.totalpage=res.data.totalpage
			this.count=res.data.count
		},
		async boardCommentListData(board_no){
			this.board_no=board_no
			const res=await api.get('/reply/list_vue', {
				params:{
					page:this.curpage,
					board_no:board_no
				}
			})
			this.setCommentData(res)
		},
		async boardCommentInsert(msgRef){
			if(this.msg==='') {
				msgRef?.focus()
				return 
			}
			const res=await api.post('/reply/insert_vue', {
				page: this.curpage,
				board_no:this.board_no,
				msg:this.msg
			})
			this.setCommentData(res)
			this.msg=''
		},
		toggleReply(no){
			this.reReplyNo=this.reReplyNo===no?null:no
		},
		async boardCommentReplyInsert(no){
			const res=await api.post('/reply/rereply_insert_vue',{
				no:no,
				board_no:this.board_no,
				page:this.curpage,
				msg:this.replyMsg[no]
			})
			this.setCommentData(res)
			this.reReplyNo=null
			this.replyMsg[no]=''
		},
		showToast(message){
			const toast=document.getElementById("replyToast")
			const toastMsg=document.getElementById("toastMsg")
			toastMsg.innerText=message
			toast.classList.add("show")
			
			setTimeout(()=>{
				hideToast()
			},5000)
		}
	}
})
const hideToast=()=>{
	const toast=document.getElementById("replyToast")
	toast.classList.remove("show")
}