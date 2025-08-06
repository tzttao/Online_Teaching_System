import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
import 'font-awesome/scss/font-awesome.scss'
import TeacherContainer from '@/components/TeacherContainer'
import StudentContainer from '@/components/StudentContainer'
import TeacherLogin from '@/views/Teacher/TeacherLogin'
import TeacherRegister from '@/views/Teacher/TeacherRegister'
import TeacherCourse from '@/views/Teacher/TeacherCourse'
import TeacherCurriculum from'@/views/Teacher/TeacherCurriculum'
import CourseWare from'@/views/Teacher/CourseWare'
import TeacherHomework from'@/views/Teacher/TeacherHomework'
import StudentHomework from'@/views/Teacher/StudentHomework'
import StudentSelection from'@/views/Teacher/StudentSelection'
import TeacherCourseScore from '@/views/Teacher/TeacherCourseScore'
import StudentScore from '@/views/Teacher/StudentScore'
import TeacherQuestion from '@/views/Teacher/TeacherQuestion'
import SelectStudentQuestion from '@/views/Teacher/StudentQuestion'
import AnswerQuestion from '@/views/Teacher/AnswerQuestion'
import TeacherInfo from '@/views/Teacher/TeacherInfo'
import TeacherPassword from '@/views/Teacher/TeacherPassword'

import StudentLogin from '@/views/Student/StudentLogin'
import StudentRegister from '@/views/Student/StudentRegister'
import StudentCourse from '@/views/Student/StudentCourse'
import StudentCurriculum from '@/views/Student/StudentCurriculum'
import StudentCourseWare from'@/views/Student/StudentCourseWare'
import StudentHomeworkFromS from'@/views/Student/StudentHomework'
import MyHomework from'@/views/Student/MyHomework'
import StudentCourseScore from '@/views/Student/StudentCourseScore'
import MyScore from '@/views/Student/MyScore'
import MyQuestion from '@/views/Student/MyQuestion'
import StudentQuestion from '@/views/Student/StudentQuestion'
import AddQuestion from '@/views/Student/AddQuestion'
import StudentQuestionByQid from '@/views/Student/StudentQuestionByQid'
import StudentInfo from '@/views/Student/StudentInfo'
import StudentPassword from '@/views/Student/StudentPassword'

Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  routes: [
    {
      path: '/TeacherLogin',
      name: 'TeacherLogin',
      component: TeacherLogin
    },
    {
      path: '/TeacherRegister',
      name: 'TeacherRegister',
      component: TeacherRegister
    },
    {
      path: '/StudentLogin',
      name: 'StudentLogin',
      component: StudentLogin
    },
    {
      path: '/StudentRegister',
      name: 'StudentRegister',
      component: StudentRegister
    },
    {
      path: '/TeacherContainer',
      name: 'TeacherContainer',
      meta: {
        /*标明需要登录*/
        auth: true
      },
      component: TeacherContainer,
      children: [
        { path: 'TeacherCourse',  component: TeacherCourse, },
        { path: 'TeacherCurriculum',  component: TeacherCurriculum, },
        { path: 'CourseWare',  component: CourseWare, },
        { path: 'TeacherHomework',  component: TeacherHomework, },
        { path: 'StudentHomework',  component: StudentHomework, },
        { path: 'StudentSelection',  component: StudentSelection, },
        { path: 'TeacherCourseScore',  component: TeacherCourseScore, },
        { path: 'StudentScore',  component: StudentScore, },
        { path: 'TeacherQuestion',  component: TeacherQuestion, },
        { path: 'SelectStudentQuestion',  component: SelectStudentQuestion, },
        { path: 'AnswerQuestion',  component: AnswerQuestion, },
        { path: 'TeacherInfo',  component: TeacherInfo, },
        { path: 'TeacherPassword',  component: TeacherPassword, },
      ]
    },
    {
      path: '/StudentContainer',
      name: 'StudentContainer',
      meta: {
        /*标明需要登录*/
        auth: true
      },
      component: StudentContainer,
      children: [
        { path: 'StudentCourse', component: StudentCourse, },
        { path: 'StudentCurriculum', component: StudentCurriculum, },
        { path: 'StudentCourseWare', component: StudentCourseWare, },
        { path: 'StudentHomeworkFromS', component: StudentHomeworkFromS, },
        { path: 'MyHomework', component: MyHomework, },
        { path: 'StudentCourseScore', component: StudentCourseScore, },
        { path: 'MyScore', component: MyScore, },
        { path: 'StudentQuestion', component: StudentQuestion, },
        { path: 'MyQuestion', component: MyQuestion, },
        { path: 'AddQuestion', component: AddQuestion, },
        { path: 'StudentQuestionByQid',  component: StudentQuestionByQid, },
        { path: 'StudentInfo',  component: StudentInfo, },
        { path: 'StudentPassword',  component: StudentPassword, },
      ]
    },

  ]
})