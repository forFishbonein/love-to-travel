import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'

const route: Route[] = [
    {
        path: '/userManage',
        component: Layout,
        redirect: '/userManage/menu',
        meta: {title: 'message.menu.userManage.name', icon: 'sfont system-xitongzhuangtai'},
        alwayShow: true,
        children: [
            {
                path: 'user',
                component: createNameComponent(() => import('@/views/main/userManage/users/index.vue')),
                meta: {title: 'message.menu.userManage.user'}
            }, {
                path: 'admin',
                component: createNameComponent(() => import('@/views/main/userManage/admin/index.vue')),
                meta: {title: 'message.menu.userManage.admin'}
            }, {
                path: 'log',
                component: createNameComponent(() => import('@/views/main/userManage/log/index.vue')),
                meta: {title: 'message.menu.userManage.log'}
            }
        ]
    }
]

export default route