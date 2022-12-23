<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import {
  getOneNoteInfoById,
  getNotesInfoByUserId,
} from "@/apis/travelService/note";
import { getUserInfoById } from "@/apis/userService/user";
import { getOneUserPlansInfoById } from "@/apis/travelService/plan";
import {
  getCommentsByNoteId,
  likeTheComment,
  islikeTheComment,
  unlikeTheComment,
  addAComment,
} from "@/apis/travelService/comment";
import {
  likeTheNote,
  islikeTheNote,
  unlikeTheNote,
  starTheNote,
  isstarTheNote,
  unstarTheNote,
  deleteOneNote,
  getRelatedOtherNotes,
} from "@/apis/travelService/note";
import { getFootsByUserId } from "@/apis/travelService/foot";
import {
  theNotesInfoType,
  theNoteComment,
  tranformComments,
  secondComment,
} from "@/apis/interface/myInterface";
import { UserInfo } from "@/apis/userService/uinterface";
import { finalAllCityPlansInfoType } from "@/apis/interface/myInterface";
import { numberFormat } from "@/utils/filters/number";
import { timeFormat } from "@/utils/filters/time";
import { strFormat } from "@/utils/filters/string";
import { useRouter } from "vue-router";
import { mainStore } from "@/store/user";
import { commentParams } from "@/apis/travelService/tInterface";
import { Picture as IconPicture } from "@element-plus/icons-vue";
const router = useRouter();
const store = mainStore();
const props = defineProps<{
  noteId: string;
}>();
const noteId = props.noteId;
const noteInfo = ref({} as theNotesInfoType);
const oneUserPlansInfo = ref({} as finalAllCityPlansInfoType);
let planId = "";
let userId = "";
const userBeensInfo = ref([]);
const otherNotesInfo = ref([] as theNotesInfoType[]);
const authorInfo = ref({} as UserInfo);
const noteCommentsInfo = ref([] as theNoteComment[]);
const finalCommentsArray = ref([] as tranformComments[]);
const commentsFormat = (data: theNoteComment[]) => {
  let oneArray = [] as theNoteComment[];
  data.forEach((e) => {
    if (e.parentId === "0" || e.parentId === "" || e.parentId === null) {
      // @ts-ignore
      finalCommentsArray.value.push({
        id: e.id,
        userId: e.userId,
        userName: e.userName,
        content: e.content,
        like: e.like,
        createTime: e.createTime,
        reply: e.reply,
        son: [],
      });
    } else {
      // @ts-ignore
      oneArray.push(e);
    }
  });
  oneArray.forEach((e) => {
    // @ts-ignore
    finalCommentsArray.value.forEach((e2: tranformComments) => {
      // @ts-ignore
      if (e.parentId === e2.id) {
        e2.son.push({
          id: e.id,
          userId: e.userId,
          userName: e.userName,
          content: e.content,
          like: e.like,
          createTime: e.createTime,
          reply: e.reply,
        });
      }
    });
  });
};
const requestOneNoteInfoAndOthers = async () => {
  await getOneNoteInfoById(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        noteInfo.value = res.data;
        planId = res.data.planId;
        userId = res.data.userId;
        console.log(noteInfo.value);
        // console.log(planId);
        // alert(planId);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  initDeleteButton();
  await getOneUserPlansInfoById(planId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        oneUserPlansInfo.value = res.data;
        console.log(oneUserPlansInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getFootsByUserId(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        if (res.data && res.data.beens) {
          userBeensInfo.value = res.data.beens;
          console.log(userBeensInfo.value);
        }
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getNotesInfoByUserId(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        otherNotesInfo.value = res.data.slice(0, 3); //长度不够不会报错
        console.log(otherNotesInfo.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getUserInfoById(userId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        authorInfo.value = res.data;
        console.log(otherNotesInfo.value);
        // alert(authorInfo.value.url);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  await getCommentsByNoteId(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        noteCommentsInfo.value = res.data;
        console.log(noteCommentsInfo.value);
        commentsFormat(noteCommentsInfo.value);
        console.log("-----------");
        console.log(finalCommentsArray.value);
        console.log("-----------");
        finalCommentsArray.value.forEach((e) => {
          // alert("加入");
          // @ts-ignore
          commentsLikeFlagList.value.push({
            id: e.id,
            likeFlag: false,
          });
        });
        console.log("++++++++++");
        console.log(commentsLikeFlagList.value);
        console.log("++++++++++");
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};
requestOneNoteInfoAndOthers();

//存储每一条评论的点赞标志变量
const commentsLikeFlagList = ref([]);
// const isLikeFlag = ref(false);
/* 是否点赞过的标志 */
const returnTheIsLikeFlag = computed(() => {
  return function (commentId: string, index: number) {
    commentisLike(commentId);
    // @ts-ignore
    // alert(commentsLikeFlagList.value[index].likeFlag + "....");
    // @ts-ignore
    return commentsLikeFlagList.value[index]?.likeFlag;
  };
});
/* axios同步请求 */
const commentisLike = (commentId: string) => {
  if (store.userInfo.id) {
    // alert("是否点赞");
    islikeTheComment(commentId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("请求完成");
          commentsLikeFlagList.value.forEach((e) => {
            // @ts-ignore
            if (commentId === e.id) {
              // @ts-ignore
              e.likeFlag = res.data;
            }
          });
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
// const returnTheIsLikeFlag = (commentId: string) => {
//   commentisLike(commentId).then((res) => {
//     isLikeFlag = res;
//   });
//   return isLikeFlag;
// };
// const cLikeFlag = ref(false);
const commentLike = async (commentId: string) => {
  if (store.userInfo.id) {
    // alert("点赞");
    await likeTheComment(commentId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "点赞成功",
          });
          setTimeout(
            () =>
              //更新评论：这里不用更新commentsLikeFlagLis，因为数据更新了就会自动重新请求显示是否点赞的标识
              getCommentsByNoteId(noteId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    //这里必须要清空一下
                    // commentsLikeFlagList.value = [];
                    noteCommentsInfo.value = [] as theNoteComment[];
                    finalCommentsArray.value = [] as tranformComments[];
                    noteCommentsInfo.value = res.data;
                    commentsFormat(noteCommentsInfo.value);
                    // alert(finalCommentsArray.value[0].like);
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                }),
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "爱宝儿，登录后才能点赞哦~",
    });
  }
};

const cancelCommentLike = async (commentId: string) => {
  // alert("取消点赞");
  if (store.userInfo.id) {
    // alert("点赞");
    await unlikeTheComment(commentId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "取消点赞成功",
          });
          setTimeout(
            () => {
              getCommentsByNoteId(noteId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    //这里必须要清空一下
                    // commentsLikeFlagList.value = [];
                    noteCommentsInfo.value = [] as theNoteComment[];
                    finalCommentsArray.value = [] as tranformComments[];
                    noteCommentsInfo.value = res.data;
                    commentsFormat(noteCommentsInfo.value);
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                });
            },
            //更新评论
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
  // 没登录就不可能取消点赞
  //  else {
  //   //@ts-ignore
  //   ElMessage({
  //     type: "warning",
  //     message: "请先登录！",
  //   });
  // }
};

const secondCommentContent = ref("");
const addSecondComment = async (pId: string) => {
  // alert(pId);
  if (store.userInfo.id) {
    const secondCommentParams: commentParams = {
      parentId: pId,
      content: secondCommentContent.value,
      userId: store.userInfo.id,
      // @ts-ignore
      userName: store.userInfo.name,
      noteId: noteId,
    };
    console.log("============");
    console.log(secondCommentParams);
    console.log("============");
    await addAComment(secondCommentParams)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "评论成功",
          });
          setTimeout(
            () =>
              //更新评论
              getCommentsByNoteId(noteId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    //这里必须要清空一下
                    noteCommentsInfo.value = [] as theNoteComment[];
                    finalCommentsArray.value = [] as tranformComments[];
                    noteCommentsInfo.value = res.data;
                    commentsFormat(noteCommentsInfo.value);
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                }),
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "爱宝儿，登录后才能评论哦~",
    });
  }
};

const firstCommentContent = ref("");
const addFirstComment = async () => {
  // alert(pId);
  if (store.userInfo.id) {
    const firstCommentParams: commentParams = {
      parentId: "",
      content: firstCommentContent.value,
      userId: store.userInfo.id,
      // @ts-ignore
      userName: store.userInfo.name,
      noteId: noteId,
    };
    console.log("============");
    console.log(firstCommentParams);
    console.log("============");
    await addAComment(firstCommentParams)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "评论成功",
          });
          setTimeout(
            () =>
              //更新评论
              getCommentsByNoteId(noteId)
                .then((res: any) => {
                  if (res.code != 0) {
                    //@ts-ignore
                    ElMessage({
                      type: "error",
                      message: res.msg,
                    });
                  } else {
                    //这里必须要清空一下
                    noteCommentsInfo.value = [] as theNoteComment[];
                    finalCommentsArray.value = [] as tranformComments[];
                    noteCommentsInfo.value = res.data;
                    commentsFormat(noteCommentsInfo.value);
                  }
                })
                .catch((error) => {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: error.message,
                  });
                }),
            1000
          );
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "爱宝儿，登录后才能评论哦~",
    });
  }
};
const noteLikeFlag = ref(false);
const noteisLike = async (noteId: string) => {
  if (store.userInfo.id) {
    await islikeTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("成功");
          noteLikeFlag.value = res.data;
          console.log(noteLikeFlag.value);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
noteisLike(noteId);
const noteLike = async () => {
  if (store.userInfo.id) {
    // alert("点赞");
    await likeTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "点赞成功",
          });
          setTimeout(async () => {
            /* 更新点赞数量 */
            await getOneNoteInfoById(noteId)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  noteInfo.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
            /* 更新点赞状态 */
            await islikeTheNote(noteId, store.userInfo.id)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  // alert("成功");
                  // alert(res.data);
                  noteLikeFlag.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
          }, 1000);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "爱宝儿，登录后才能点赞哦~",
    });
  }
};
const cancelNoteLike = async () => {
  // alert("取消点赞");
  if (store.userInfo.id) {
    await unlikeTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "取消点赞成功",
          });
          setTimeout(async () => {
            await getOneNoteInfoById(noteId)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  noteInfo.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
            await islikeTheNote(noteId, store.userInfo.id)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  // alert("成功");
                  noteLikeFlag.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
          }, 1000);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
/* 游记收藏 */
const noteStarFlag = ref(false);
const noteisStar = async (noteId: string) => {
  if (store.userInfo.id) {
    await isstarTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          // alert("成功");
          noteStarFlag.value = res.data;
          console.log(noteStarFlag.value);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
noteisStar(noteId);
const noteStar = async () => {
  if (store.userInfo.id) {
    // alert("点赞");
    await starTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "收藏成功",
          });
          setTimeout(async () => {
            await getOneNoteInfoById(noteId)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  noteInfo.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
            await isstarTheNote(noteId, store.userInfo.id)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  // alert("成功");
                  noteStarFlag.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
          }, 1000);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  } else {
    //@ts-ignore
    ElMessage({
      type: "warning",
      message: "爱宝儿，登录后才能收藏哦~",
    });
  }
};
const cancelNoteStar = async () => {
  // alert("取消点赞");
  if (store.userInfo.id) {
    await unstarTheNote(noteId, store.userInfo.id)
      .then((res: any) => {
        if (res.code != 0) {
          //@ts-ignore
          ElMessage({
            type: "error",
            message: res.msg,
          });
        } else {
          //@ts-ignore
          ElMessage({
            type: "success",
            message: "取消收藏成功",
          });
          setTimeout(async () => {
            await getOneNoteInfoById(noteId)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  noteInfo.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
            await isstarTheNote(noteId, store.userInfo.id)
              .then((res: any) => {
                if (res.code != 0) {
                  //@ts-ignore
                  ElMessage({
                    type: "error",
                    message: res.msg,
                  });
                } else {
                  // alert("成功");
                  noteStarFlag.value = res.data;
                }
              })
              .catch((error) => {
                //@ts-ignore
                ElMessage({
                  type: "error",
                  message: error.message,
                });
              });
          }, 1000);
        }
      })
      .catch((error) => {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: error.message,
        });
      });
  }
};
const belongToMeFlag = ref(false);
const deleteConfirmDialogVisible = ref(false);
const initDeleteButton = () => {
  if (store.userInfo.id) {
    if (userId === store.userInfo.id) {
      belongToMeFlag.value = true;
    }
  }
};
const openTheDeleteConfirmDialog = () => {
  deleteConfirmDialogVisible.value = true;
};
const deleteTheNote = () => {
  deleteOneNote(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        router.push("/personal/mynote");
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "删除成功",
        });
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};
const relatedNotesInfo = ref([] as theNotesInfoType[]);
const haveRelatedNoteflag = ref(false);
const requestRelatedOtherNotes = () => {
  getRelatedOtherNotes(noteId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        relatedNotesInfo.value = res.data;
        if (res.data !== null && res.data.length > 0) {
          haveRelatedNoteflag.value = true;
        }
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};
onMounted(() => {
  requestRelatedOtherNotes();
});
</script>
<template>
  <section class="news-details">
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-7">
          <div class="news-details__left">
            <div class="news-details__img">
              <img :src="noteInfo.url" alt="" />
              <div class="news-one__date">
                <p>
                  <span>{{ timeFormat(noteInfo.createTime) }}</span>
                </p>
              </div>
            </div>
            <div class="news-details__content">
              <ul class="list-unstyled news-one__meta other-style">
                <li style="font-size: 16px">
                  <router-link :to="`/user/${noteInfo.userId}`"
                    ><i class="far fa-user-circle"></i>发布人:<span
                      class="span-style"
                      style="margin-left: 5px"
                      >{{ noteInfo.userName }}</span
                    ></router-link
                  >
                </li>
                <li style="font-size: 16px">
                  <el-icon size="16px" :color="`#e8604c`"
                    ><OfficeBuilding
                  /></el-icon>
                  相关城市:
                  <span
                    class="span-style"
                    style="font-size: 14px; margin-left: 5px"
                    >{{ noteInfo.city }}</span
                  >
                </li>
                <li style="font-size: 16px">
                  <el-icon size="16px" :color="`#e8604c`"><Calendar /></el-icon>
                  发布时间:
                  <span
                    class="span-style"
                    style="font-size: 14px; margin-left: 5px"
                    >{{ noteInfo.createTime }}</span
                  >
                </li>
              </ul>
              <h3 class="news-details__title">
                {{ noteInfo.title }}
              </h3>
              <el-scrollbar max-height="700px" class="content-body-note">
                <p class="news-details__text-1" v-html="noteInfo.content"></p>
              </el-scrollbar>
            </div>
            <div class="news-details__bottom">
              <p class="news-details__tags">
                <span>Tags:</span>
                <a href="javascript:;" v-for="item in noteInfo.trip">{{
                  item
                }}</a>
              </p>
              <ul class="list-unstyled news-one__meta other-style">
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><View /></el-icon>浏览:{{
                      numberFormat(noteInfo.view)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <svg
                      class="icon"
                      aria-hidden="true"
                      v-if="noteLikeFlag"
                      @click="cancelNoteLike"
                    >
                      <use xlink:href="#icon-dianzan1"></use>
                    </svg>
                    <svg
                      class="icon"
                      aria-hidden="true"
                      v-else
                      @click="noteLike"
                    >
                      <use xlink:href="#icon-dianzan"></use>
                    </svg>
                    点赞:{{ numberFormat(noteInfo.like) }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <svg
                      class="icon"
                      aria-hidden="true"
                      v-if="noteStarFlag"
                      @click="cancelNoteStar"
                    >
                      <use xlink:href="#icon-shoucang1"></use>
                    </svg>
                    <svg
                      class="icon"
                      aria-hidden="true"
                      v-else
                      @click="noteStar"
                    >
                      <use xlink:href="#icon-shoucang"></use>
                    </svg>
                    收藏:{{ numberFormat(noteInfo.star) }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><Document /></el-icon>评论:{{
                      numberFormat(noteInfo.comment)
                    }}
                  </a>
                </li>
              </ul>
            </div>
            <div class="author-one">
              <div class="author-one__image">
                <el-image :src="authorInfo.url" alt="" class="img-avater">
                  <template #error>
                    <div class="image-slot">
                      <el-icon><icon-picture /></el-icon>
                    </div>
                  </template>
                </el-image>
                <!-- <img :src="authorInfo.url" alt="" class="img-avater" /> -->
                <!-- <img
                  src="/images/blog/author-1-1.jpg"
                  alt=""
                  class="img-avater"
                /> -->
              </div>
              <div class="author-one__content">
                <h3>
                  <router-link :to="`/user/${authorInfo.id}`">{{
                    authorInfo.name
                  }}</router-link>
                </h3>
                <p>个性签名:{{ authorInfo.signature }}</p>
              </div>
            </div>
            <div class="comment-form">
              <h3 class="comment-form__title">发表评论</h3>
              <div class="row">
                <div class="col-xl-12">
                  <div class="comment-form__input-box">
                    <textarea
                      name="message"
                      placeholder="写下评论内容"
                      v-model="firstCommentContent"
                      style="font-size: 16px"
                    ></textarea>
                    <button
                      type="submit"
                      class="thm-btn comment-form__btn"
                      @click="addFirstComment"
                    >
                      发表
                    </button>
                  </div>
                </div>
                <div class="col-xl-12" v-if="belongToMeFlag">
                  <div
                    class="delete-button"
                    @click="openTheDeleteConfirmDialog"
                  >
                    删除游记
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-lg-5">
          <div class="sidebar">
            <div class="sidebar__single sidebar__tags">
              <h3 class="sidebar__title">文章标签</h3>
              <div class="sidebar__tags-list">
                <a href="javascript:;" v-for="item in noteInfo.trip">{{
                  item
                }}</a>
              </div>
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">Ta去过的地方</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li v-for="item in userBeensInfo">
                  <router-link
                    :to="`/goTravel/city/detail/${
                      // @ts-ignore
                      item.cityId
                    }`"
                    >{{
                      // @ts-ignore
                      item.cityName
                    }}</router-link
                  >
                </li>
              </ul>
            </div>
            <div class="sidebar__single sidebar__post">
              <h3 class="sidebar__title">Ta的部分游记</h3>
              <ul
                class="sidebar__post-list list-unstyled"
                v-for="item in otherNotesInfo"
              >
                <li style="margin-bottom: 15px">
                  <div class="sidebar__post-image">
                    <img :src="item.url" alt="" />
                  </div>
                  <div class="sidebar__post-content">
                    <h3>
                      <a href="javascript:;" class="sidebar__post-content_meta"
                        ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                          numberFormat(item.like)
                        }}</a
                      >
                      <router-link :to="`${item.id}`" style="font-size: 16px">{{
                        strFormat(item.title, 30)
                      }}</router-link>
                    </h3>
                  </div>
                </li>
              </ul>
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">对应行程表</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li style="font-size: 16px">
                  总预算:{{ oneUserPlansInfo?.budget }}元
                </li>
                <li>
                  <span class="span-style text-amber">行程信息</span>
                  <el-scrollbar max-height="300px">
                    <el-collapse accordion>
                      <el-collapse-item
                        :name="index"
                        v-for="(item, index) in oneUserPlansInfo?.subPlans"
                        :key="index"
                      >
                        <template #title>
                          <div class="plan-note-header">
                            {{ item?.city
                            }}<el-icon class="header-icon" size="16px">
                              <Place />
                            </el-icon>
                            <span style="margin-left: 10px">预算:</span
                            >{{ item?.budget }}元
                          </div>
                        </template>
                        <ul>
                          <li v-for="(i, index2) in item?.days" :key="index2">
                            第{{ index2 + 1 }}天
                            <span v-for="(k, index3) in i.route" :key="index3">
                              {{ k?.originName }}({{ k?.departTime }}小时)
                              <el-icon><Right /></el-icon>
                            </span>
                            结束
                          </li>
                        </ul>
                      </el-collapse-item>
                    </el-collapse>
                  </el-scrollbar>
                </li>
              </ul>
            </div>
            <div class="make-plan-button">
              <router-link to="/plan" class="about-one__btn thm-btn"
                >制定专属行程</router-link
              >
            </div>
            <div class="sidebar__single sidebar__category">
              <h3 class="sidebar__title">游记评论</h3>
              <ul class="sidebar__category-list list-unstyled">
                <li>
                  <el-scrollbar max-height="800px" style="padding-right: 10px">
                    <el-collapse accordion>
                      <el-collapse-item
                        :name="index"
                        v-for="(item, index) in finalCommentsArray"
                        :key="index"
                      >
                        <template #title>
                          <div class="comment-container">
                            <div class="comment-header">
                              <p>
                                <router-link :to="`/user/${item.userId}`">{{
                                  item.userName
                                }}</router-link>
                              </p>
                              <p>
                                {{ item.createTime }}
                              </p>
                            </div>
                            <div class="comment-body">
                              <el-scrollbar max-height="40px">
                                <div class="content-main">
                                  {{ item.content }}
                                </div>
                              </el-scrollbar>
                            </div>
                            <div class="function-button-container">
                              <div>
                                <svg
                                  class="icon"
                                  aria-hidden="true"
                                  @click="cancelCommentLike(item.id)"
                                  v-if="returnTheIsLikeFlag(item.id, index)"
                                >
                                  <use xlink:href="#icon-dianzan1"></use>
                                </svg>
                                <svg
                                  class="icon"
                                  aria-hidden="true"
                                  @click="commentLike(item.id)"
                                  v-else
                                >
                                  <use xlink:href="#icon-dianzan"></use>
                                </svg>
                                {{ item.like }}
                              </div>
                              <div>
                                <el-icon
                                  size="22px"
                                  :color="`#e8604c`"
                                  style="padding-right: 5px"
                                  ><ChatSquare
                                /></el-icon>
                                {{ item.reply }}
                              </div>
                              <div>
                                <el-popover
                                  placement="right"
                                  :width="70"
                                  trigger="click"
                                >
                                  <template #reference>
                                    <el-button class="second-button-one"
                                      >评论</el-button
                                    >
                                  </template>
                                  <el-input
                                    v-model="secondCommentContent"
                                    type="textarea"
                                  />
                                  <el-button
                                    class="second-button-two"
                                    @click="addSecondComment(item.id)"
                                    >确认</el-button
                                  >
                                </el-popover>
                              </div>
                            </div>
                          </div>
                        </template>
                        <el-scrollbar max-height="400px">
                          <ul class="second-ul">
                            <li
                              v-for="(i, index2) in item.son"
                              :key="index2"
                              class="second-item"
                            >
                              <p>
                                <router-link :to="`/user/${i.userId}`"
                                  ><span style="color: #e8604c">{{
                                    i.userName
                                  }}</span></router-link
                                >
                                &nbsp;
                                <span style="color: #909399">{{
                                  i.createTime
                                }}</span>
                              </p>
                              <p>{{ i.content }}</p>
                            </li>
                          </ul>
                        </el-scrollbar>
                      </el-collapse-item>
                    </el-collapse>
                  </el-scrollbar>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section class="news-one" v-show="haveRelatedNoteflag">
    <div class="container">
      <div class="row">
        <div class="section-title text-left">
          <span class="section-title__tagline">Related travel notes </span>
          <h2 class="section-title__title ali-font-family">其他相关游记</h2>
        </div>
        <div
          class="col-xl-4 col-lg-6 col-md-6 fadeInUp"
          data-wow-delay="100ms"
          v-for="(item, index) in relatedNotesInfo"
          :key="item.id"
        >
          <!--News One Single-->
          <div class="news-one__single">
            <div class="news-one__img">
              <img :src="item.url" alt="" class="notes-img" />
              <router-link :to="`/readTravel/note/detail/${item.id}`">
                <span class="news-one__plus"></span>
              </router-link>
              <div class="news-one__date">
                <p>
                  <span>{{ timeFormat(item.createTime) }}</span>
                </p>
              </div>
            </div>
            <div class="news-one__content">
              <ul class="list-unstyled2 news-one__meta">
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><View /></el-icon>浏览:{{
                      numberFormat(item.view)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Pointer /></el-icon>点赞:{{
                      numberFormat(item.like)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;"
                    ><el-icon size="20px"><Star /></el-icon>收藏:{{
                      numberFormat(item.star)
                    }}</a
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><Document /></el-icon>评论:{{
                      numberFormat(item.comment)
                    }}
                  </a>
                </li>
                <li>
                  <router-link :to="`/user/${item.userId}`"
                    ><i class="far fa-user-circle"></i
                    ><span class="span-style">{{
                      item.userName
                    }}</span></router-link
                  >
                </li>
                <li>
                  <a href="javascript:;">
                    <el-icon size="20px"><OfficeBuilding /></el-icon>相关城市:{{
                      item.city
                    }}
                  </a>
                </li>
              </ul>
              <h3 class="news-one__title">
                <router-link :to="`/readTravel/note/detail/${item.id}`">{{
                  item.title
                }}</router-link>
              </h3>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <el-dialog
    v-model="deleteConfirmDialogVisible"
    title="警告框"
    width="30%"
    center
  >
    <span> 您将要删除该游记，确定继续吗？(该操作不可逆) </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteConfirmDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="deleteTheNote"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
/* 相关游记开始 */
.news-details {
  padding-bottom: 50px;
}
.news-one {
  padding-top: 0px;
}
.list-unstyled2 {
  display: flex;
  flex-wrap: wrap;
  > li {
    margin-left: 5px;
    display: flex;
    justify-content: center;
    > a {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
.notes-img {
  width: 100%;
  height: 100%;
}
.news-one__img {
  width: 350px;
  height: 300px;
}
/* 相关游记结束 */
.delete-button {
  width: 150px;
  height: 40px;
  float: right;
  background-color: #e6a23c;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  cursor: pointer;
  font-weight: 700;
}
.delete-button:hover {
  background-color: #b88230;
}
.news-details {
  padding-top: 70px;
}
.other-style {
  display: flex;
  flex-wrap: wrap;
  margin: 20px 0;
  > li {
    margin-left: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    > a {
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 2em;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px;
}
/* 指定行程按钮样式 */
.make-plan-button {
  width: 100%;
  height: auto;
  display: flex;
  margin: 20px 0;
  justify-content: center;
}
.text-amber {
  font-size: 16px;
}
.comment-form__title {
  margin-bottom: 20px;
}
.comment-one {
  margin-top: 40px;
}
//样式穿透
::v-deep .el-collapse-item__header {
  display: flex;
  // flex-direction: column;
  justify-content: space-around;
  // flex-wrap: wrap;
  // height: 200px;
  // line-height: 200px;
  height: auto;
  padding: 10px 0;
}
.comment-container {
  .comment-header {
    height: 50px;
    width: 230px;
    // width: 100%;
    // border: 1px #e8604c solid;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
    border-bottom: 1px #dcdfe6 solid;
    margin-bottom: 10px;
    > p {
      margin: 0;
      line-height: 1.5em;
    }
    > p:first-child {
      a {
        color: #e8604c;
        font-weight: 700;
      }
    }
    > p:nth-child(2) {
      font-size: 12px;
      color: #909399;
    }
  }
  .comment-body {
    // border: 1px #e8604c solid;
    height: 60px;
    width: 230px;
    // display: flex;
    // justify-content: start;
    // align-items: center;
    .content-main {
      line-height: 1.5em;
      color: #606266;
    }
  }
  .function-button-container {
    margin-top: 10px;
    height: 30px;
    width: 230px;
    // border: 1px #e8604c solid;
    display: flex;
    align-items: center;
    > div {
      width: 70px;
      height: 30px;
      // border: 1px #e8604c solid;
      display: flex;
      align-items: center;
      // justify-content: center;
      color: #606266;
      font-size: 16px;
    }
    // > div:first-child {
    //   // width: auto;
    //   // height: 1.2em;
    //   // border: 1px #e8604c solid;
    // }
  }
}
.plan-note-header {
  width: 100%;
  height: auto;
  // border: 1px #e8604c solid;
  display: flex;
  align-items: center;
}
.second-ul {
  padding-left: 5px;
}
.second-item {
  list-style: none;
  > p {
    margin: 0;
    line-height: 1.5em;
  }
  > p:first-child > span {
    display: inline-block;
    margin-right: 10px;
  }
}
.second-button-one {
  height: 30px;
}
.second-button-two {
  margin-top: 10px;
  height: 30px;
  float: right;
}
.img-avater {
  width: 150px;
  height: 150px;
}
.content-body-note {
  border: 1px #dcdfe6 solid;
}
.sidebar__post-image {
  width: 60px;
  height: 65px;
  img {
    display: block;
    width: 60px;
    height: 65px;
  }
}
</style>
