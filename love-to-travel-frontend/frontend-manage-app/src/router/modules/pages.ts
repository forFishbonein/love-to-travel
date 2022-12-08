import type {Route} from '../index.type'
import Layout from '@/layout/index.vue'
import {createNameComponent} from '../createNode'

const route: Route[] = [
    {
        path: '/pages',
        component: Layout,
        redirect: '/pages/crudTable',
        meta: {title: 'message.menu.page.name', icon: 'sfont system-page'},
        alwayShow: true,
        children: [
            {
                path: 'city',
                component: createNameComponent(() => import('@/views/main/pages/city/index.vue')),
                meta: {title: 'message.menu.page.city', cache: false, roles: ['admin', 'editor']}
            }, {
                path: 'scenery',
                component: createNameComponent(() => import('@/views/main/pages/scenery/index.vue')),
                meta: {title: 'message.menu.page.scenery', cache: false, roles: ['admin', 'editor']}
            }, {
                path: 'note',
                component: createNameComponent(() => import('@/views/main/pages/note/index.vue')),
                meta: {title: 'message.menu.page.note', cache: false, roles: ['admin', 'editor']}
            },{
                path: 'plan',
                component: createNameComponent(() => import('@/views/main/pages/plan/index.vue')),
                meta: {title: 'message.menu.page.plan', cache: false, roles: ['admin', 'editor']}
            },

        ]
    }
]

export default route