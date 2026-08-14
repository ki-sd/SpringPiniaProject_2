const {defineStore}=Pinia
// store : 저장공간=(처리함수,데이터)
// => 전역으로 사용=모든 HTML에서 사용 가능
// => 데이터가 변경 => 자동으로 HTML 갱신
// => --------------------------- state
const initialState=()=>({
	list:[],
	curpage:1,
	totalpage:0,
	startPage:0,
	endPage:0,
	count:0,
	type:'',
	fd:'',
	detail:{}
})
// store 생성 => 새로운 store 생성시 => defineStore
const useFoodStore=defineStore('food_store',{
	state:initialState,
	getters:{
		range:(state)=>{
			const arr=[]
			for(let i=state.startPage;i<=state.endPage;i++){
				arr.push(i)
			}
			return arr
		}
	},
	actions:{
		async foodListData(){
			const res=await api.get('/food/list_vue',{
				params:{
					page:this.curpage
				}
			})
			console.log(res.data)
			this.setPageData(res.data)
		},
		setPageData(data){
			this.list=data.list
			this.curpage=data.curpage
			this.totalpage=data.totalpage
			this.startPage=data.startPage
			this.endPage=data.endPage
			this.count=data.count
		},
		move(page){
			this.curpage=page
			this.foodListData()
		}
	}
})