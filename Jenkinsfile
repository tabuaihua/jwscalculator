pipeline{
    agent any
    stages{
        stage('拉取Gitea源码') {
            steps {
                git credentialsId: 'gitea', url: 'http://116.204.84.48:3000/gitea/jwscalculator.git'
            }
        }
        stage('编译Java源码') {
            steps{
                bat 'javac *.java -d .'
            }
        }
        stage('生成jar包') {
            steps{
                bat 'jar cfe jwscalculator.jar gitops.jwscalculator.JwsCalculator gitops/jwscalculator/*.class'
            }
        }
        stage('jpackage.exe生成exe和msi'){
            steps{
                bat '''mkdir app
                copy jwscalculator.jar app
                jpackage -i .\\app --type app-image -n jwscalculator_exe --main-jar jwscalculator.jar --main-class gitops.jwscalculator.JwsCalculator --vendor dll --verbose --win-console
                jpackage -i .\\app --type msi -n jwscalculator_msi --main-jar jwscalculator.jar --main-class gitops.jwscalculator.JwsCalculator --vendor dll --verbose --win-console --win-dir-chooser --win-shortcut --win-menu'''
            }
        }
        stage('keytool生成密钥'){
            steps{
                bat 'keytool -genkey -alias mykey -keystore mykeystore.store -storetype PKCS12 -keyalg RSA -storepass mystorepass  -validity 365 -keysize 2048 -dname "CN=liudongliang, OU=chzu, L=xxxy, S=chuzhou, O=anhui, C=CH"'
            }
        }
        stage('jarsigner签名jwscalculator.jar') {
            steps {
                bat 'jarsigner -keystore myKeystore.store jwscalculator.jar mykey -storepass mystorepass'
            }
        }
        stage('测试阶段...'){
            steps{
                echo "Test Stage"
            }
        }
        stage('打包War(含exe和msi)') {
            steps {
                bat 'jar cfM jwscalculator.war  index.html jwscalculator.jnlp jwscalculator.jar jwscalculator_exe/jwscalculator_exe.exe jwscalculator_msi-1.0.msi'
            }
        }
        stage('部署War至tomcat'){
            steps{
                 deploy adapters: [tomcat9(credentialsId: 'tomcat', path: '', url: 'http://116.204.84.48:8080')], contextPath: '/jwscalculator', war: 'jwscalculator.war'
            }
        }
        stage('运行exe'){
            steps{
               bat '.\\jwscalculator_exe\\jwscalculator_exe.exe'
            }
        }
        stage('运行msi'){
            steps{
               bat '.\\jwscalculator_msi-1.0.msi'
            }
        }
        stage('WebStart运行计算器(OpenWebStart)'){
            steps{
               bat '"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe" http://116.204.84.48:8080/jwscalculator/index.html' 
            }
        }
        stage('清除'){
            steps{
                bat '''rd /S /Q gitops app jwscalculator_exe
                    del mykeystore.store
                    del jwscalculator.jar
                    del jwscalculator.war
                    del jwscalculator_msi-1.0.msi'''
            }
        }
    }   
}