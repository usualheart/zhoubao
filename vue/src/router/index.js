import Vue from 'vue'
import Router from 'vue-router'
/*
//传统没有配置路由懒加载的引用配置
import Login from '@/components/Login'

import Header from '@/components/Header'
import TimeLine from '@/components/TimeLine'
import UploadPdf from '@/components/UploadPdf'
import Help from '@/components/Help'
import Footer from '@/components/Footer'


import Markdown from '../components/Markdown' //这个是与Markdown相关的组件
import PdfViewer from '@/components/PdfViewer'
import MdViewer from '@/components/MdViewer'
import HtmlViewer from '../components/onlinezhoubao/HtmlViewer'
import HtmlEdit from '../components/onlinezhoubao/HtmlEdit'

import AdminHeader from '@/components/AdminHeader'
import AdminTimeLine from '@/components/AdminTimeLine'
import OldAdmin from '@/components/OldAdmin'
*/
Vue.use(Router)

//路由懒加载配置
function loadView(view) {
	return () => import('@/components/' + view + '.vue')
}
const Login = () => import( /* webpackChunkName: "view-[request]" */ '@/components/Login.vue')
const Header = () => import('@/components/Header.vue')
const HeaderLogin = () => import('@/components/HeaderLogin.vue')

const TimeLine = () => import('@/components/TimeLine.vue')
const UploadPdf = () => import('@/components/UploadPdf.vue')
const Help = () => import('@/components/Help.vue')
const Footer = () => import('@/components/Footer.vue')

const Markdown = () => import('@/components/Markdown.vue')
const PdfViewer = () => import('@/components/PdfViewer.vue')
const Viewer = () => import('@/components/Viewer.vue')
const MdViewer = () => import('@/components/MdViewer.vue')
const HtmlViewer = () => import('@/components/onlinezhoubao/HtmlViewer.vue')
const HtmlEdit = () => import('@/components/onlinezhoubao/HtmlEdit.vue')


const AllTimeLine = () => import('@/components/quan/AllTimeLine.vue')

const AdminHeader = () => import('@/components/AdminHeader.vue')
const AdminTimeLine = () => import('@/components/AdminTimeLine.vue')
const NewAdminTimeLine = () => import('@/components/NewAdminTimeLine.vue')
const MenuList = () => import('@/components/MenuList.vue')
const OldAdmin = () => import('@/components/OldAdmin.vue')
export default new Router({
	routes: [{
			path: '/',
			name: 'Login',
			components: {
				//header:HeaderLogin,
				//header: Login,
				main1: Login,
				footer: Footer
			}
		},

		{
			path: '/HomePage',
			name: 'HomePage',
			components: {
				header: Header,
				main0: TimeLine,
				footer: Footer
			},

		}, {
			path: '/AllTimeLine',
			name: 'AllTimeLine',
			components: {
				header: Header,
				main0: AllTimeLine,
				footer: Footer
			},

		},
		{
			path: '/UploadPdf',
			name: 'UploadPdf',
			components: {
				header: Header,
				main0: UploadPdf,
			}
		},
		{
			path: '/MakeMarkdown',
			name: 'MakeMarkdown',
			components: {
				header: Header,
				main0: Markdown,
				footer: Footer
			}
		},
		
		//旧的路由
		{
			path: '/MdViewer/:zhoubao',
			name: 'MdViewer',
			components: {
				header: Header,
				main0: MdViewer,
				footer: Footer
			},
			props: {
				header: false,
				main0: true,
			}
		},
		 {
			path: '/PdfViewer/:zhoubao',
			name: 'PdfViewer',
			components: {
				header: Header,
				main0: PdfViewer,
				//main1: PdfViewer,//PdfViewer
				footer: Footer
			},
			props: {
				header: false,
				main0: true,
				//main1: true,
			}
		}, 
		{
			path: '/HtmlViewer/:userName/:week',
			name: 'HtmlViewer',
			components: {
				header: Header,
				main0: HtmlViewer,
			},
			props: {
				header: false,
				main0: true,
			}
		},
		{
			path: '/HtmlEdit',
			name: 'HtmlEdit',
			components: {
				header: Header,
				main0: HtmlEdit,
				footer: Footer
		
			}
		},
		{
			path: '/Help',
			name: 'Help',
			components: {
				header: Header,
				main0: Help,
				footer: Footer
			},
		},
		{
			path: '/Viewer',
			name: 'Viewer',
			components: {
				header: Header,
				main1 : Viewer,
			},
			children: [
				// UserHome will be rendered inside User's <router-view>
				// when /user/:id is matched
				// {
				// 	path: '',
				// 	component: ''
				// },

				// UserProfile will be rendered inside User's <router-view>
				// when /user/:id/profile is matched
				{
					name: 'pdfViewerAll',
					path: 'pdf/:zhoubao/:id',
					component: PdfViewer,
					props: true,
				},

				// UserPosts will be rendered inside User's <router-view>
				// when /user/:id/posts is matched
				{
					path: 'html/:userName/:week',
					component: HtmlViewer,
					name: 'HtmlViewerAll',
					props: true
				},
				{
					name: 'MdViewerAll',
					path: 'md/:zhoubao/:id',
					component: MdViewer,

					props: true
				}
			],
			props: {
				header: false,
				main0: false,
				main1: false,
				viewer: true,
			}
		},

	{
		path: '/SeeAllZhoubao',
		name: 'SeeAllZhoubao',
		components: {
			header: Header,
			main0: NewAdminTimeLine,
		},
	},
	//管理员路由
	{
		path: '/AdminHomePage',
		name: 'AdminHomePage',
		components: {
			header: Header,
			main0: NewAdminTimeLine,
		},
	},


]
})
