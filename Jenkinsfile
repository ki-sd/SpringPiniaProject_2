pipeline{
	stages{
		stage('Git Connection Check'){
			steps{
				echo "==================="
				echo "Git 연결 확인"
				echo "==================="
				git branch: 'main',
				    url: https://github.com/ki-sd/SpringPiniaProject_2.git
				echo "==================="
				echo "Git 연결 완료"
				echo "==================="
			}
		}
	}
}