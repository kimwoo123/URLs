podTemplate(label: 'pod-golang',
	containers: [
		containerTemplate(
			name: 'golang',
			image: 'golang',
			ttyEnabled: true,
			command: 'cat'
		)
	]
) {
	node ('pod-golang') {
		stage 'Swtich to Utility'
		container('golang') {
			sh ("go version")
			sh ("minikube ip")
		}
	}
}
