import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'

const route: Route[] = [
    {
        path: '/systemManage',
        component: Layout,
        redirect: '/systemManage/menu',
        meta: {title: 'message.menu.systemManage.name', icon: 'sfont system-xitongzhuangtai'},
        alwayShow: true,
        children: [
            {
                path: 'user',
                component: createNameComponent(() => import('@/views/main/systemManage/users/index.vue')),
                meta: {title: 'message.menu.systemManage.user'}
            }, {
                path: 'admin',
                component: createNameComponent(() => import('@/views/main/systemManage/admin/index.vue')),
                meta: {title: 'message.menu.systemManage.admin'}
            }, {
                path: 'log',
                component: createNameComponent(() => import('@/views/main/systemManage/log/index.vue')),
                meta: {title: 'message.menu.systemManage.log'}
            }
        ]
    }
]

export default route