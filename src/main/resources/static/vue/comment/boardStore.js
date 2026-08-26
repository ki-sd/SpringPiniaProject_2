const {defineStore}=Pinia
const userBoardStore=defineStore('board_comment',{
	state:()=>({
		list:[],
		curpage:1,
		totalpage:0,
		board_no:0,
		sessionId:'',
		count:0,
		msg:'',
		stomp:null,
		updateMsg:{},
		updateReplyNo:null,
		replyMsg:{},
		reReplyNo:null
	}),
	actions:{
		
	}
})