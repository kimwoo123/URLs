podTemplate(containers: [
    containerTemplate(name: 'docker', image: 'docker', command: 'cat', ttyEnabled: true),
    containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl:v1.18.3', command: 'cat', ttyEnabled: true),
],
volumes: [
  hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
]) {
    node(POD_LABEL) {
        def frontrepo = "eypk9673/eagle-front2"
        def backrepo = "eypk9673/eagle-back2"

        stage ('Checkout github branch') {
            git branch: 'develop', credentialsId: 'githubcred', url: 'https://github.com/kimwoo123/URLs'
        }

        stage ('Build and Push docker image') {
            container ('docker') {
                withCredentials([[
                    $class: 'UsernamePasswordMultiBinding',
                    credentialsId: 'dockerhub_creden',
                    usernameVariable: 'DOCKER_HUB_USER',
                    passwordVariable: 'DOCKER_HUB_PASSWORD'
                ]])  {
                    sh ('echo ${DOCKER_HUB_PASSWORD} | docker login -u $DOCKER_HUB_USER --password-stdin')
                    dir ('web') {
                        try {
                            sh """
                                docker build -t ${frontrepo}:${env.BUILD_NUMBER} --network=host .
                                docker push ${frontrepo}:${env.BUILD_NUMBER}
                            """
                        } catch (e) {
                            error "frontend build fail"
                        }
                    }
                    dir ('backend') {
                        try {
                            sh """
                                docker build -t ${backrepo}:${env.BUILD_NUMBER} --network=host .
                                docker push ${backrepo}:${env.BUILD_NUMBER}
                            """
                        } catch (e) {
                            error "backend build fail"                    
                        }
                    }
                }
    		}
        }
		    
        stage('Apply kubernetes') {
            container('kubectl') {
                sh """
                     kubectl set image deployment web-front web-front=${frontrepo}:${env.BUILD_NUMBER} -n ingress-nginx
                     kubectl set image deployment eagle-back eagle-back=${backrepo}:${env.BUILD_NUMBER} -n ingress-nginx
                """
            }
        }
    }
}
