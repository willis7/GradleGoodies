def convertToCelsius(fValue) {
	(int)(fValue - 32) * 5/9
}

[0, 32, 70, 100].each {
	println "${it} F equals ${convertToCelsius(it)} C"
}