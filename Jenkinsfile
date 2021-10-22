podTemplate(containers: [
    containerTemplate(name: 'node', image:'node:10.20.1', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.18.3', command: 'cat', ttyEnabled: true),
],
volumes: [
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
    node(POD_LABEL) {
        def uuid = UUID.randomUUID().toString()
        def repo = "eypk9673/eagle/web-front-${uuid}"

        stage('Checkout github branch') {
            checkout scm
        }

        stage('Run test') {
			dir ('web') {
				container('node') {
					sh "npm install"
					sh "npm run build"
				}
			}
        }

        stage('Build and Push docker image') {
			dir ('web') {
				container('docker') {
					withCredentials([[
						$class: 'UsernamePasswordMultiBinding',
						credentialsId: 'dockerhub_creden',
						usernameVariable: 'DOCKER_HUB_USER',
						passwordVariable: 'DOCKER_HUB_PASSWORD'
					]])  {
						sh """
							docker login -u ${DOCKER_HUB_USER} -p ${DOCKER_HUB_PASSWORD}
							docker build -t ${repo} .
							docker push ${repo}
						"""
					}
				}
			}
        }
        stage('Apply kubernetes') {
            container('kubectl') {
                sh """
                     kubectl set image deployment web-front web-front=${image}
                """
            }
        }
    }
}
