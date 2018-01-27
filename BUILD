supportLibraryVersion = "27.0.1".replace(".", "_")

android_deps = [
    "@android_arch_core_common_1_0_0//jar",
    "@android_arch_core_runtime_1_0_0//aar",
    "@android_arch_lifecycle_common_1_0_3//jar",
    "@android_arch_lifecycle_extensions_1_0_0//aar",
    "@android_arch_lifecycle_runtime_1_0_3//aar",
    "@com_android_support_animated_vector_drawable_{}//aar".format(supportLibraryVersion),
    "@com_android_support_appcompat_v7_{}//aar".format(supportLibraryVersion),
    "@com_android_support_cardview_v7_{}//aar".format(supportLibraryVersion),
    "@com_android_support_design_{}//aar".format(supportLibraryVersion),
    "@com_android_support_recyclerview_v7_{}//aar".format(supportLibraryVersion),
    "@com_android_support_support_annotations_{}//jar".format(supportLibraryVersion),
    "@com_android_support_support_compat_{}//aar".format(supportLibraryVersion),
    "@com_android_support_support_core_ui_{}//aar".format(supportLibraryVersion),
    "@com_android_support_support_core_utils_{}//aar".format(supportLibraryVersion),
    "@com_android_support_support_fragment_{}//aar".format(supportLibraryVersion),
    "@com_android_support_support_vector_drawable_{}//aar".format(supportLibraryVersion),
    "@com_android_support_transition_{}//aar".format(supportLibraryVersion),
    "@com_sothree_slidinguppanel_library//aar",
    "@com_timehop_stickyheadersrecyclerview_library//aar",
    "@com_tonicartos_superslim//aar",
    "@de_hdodenhof_circleimageview//aar",
]

android_library(
    name = "app_res",
    assets = glob(["src/main/assets/**"]),
    assets_dir = "src/main/assets",
    custom_package = "im.tox.toktok",
    manifest = "src/main/AndroidManifest.xml",
    resource_files = glob(["src/main/res/**"]),
    deps = android_deps,
)

android_binary(
    name = "app",
    srcs = glob(["src/main/java/**/*.java"]),
    custom_package = "im.tox.toktok",
    manifest = "src/main/AndroidManifest.xml",
    proguard_specs = ["proguard-rules.pro"],
    resource_files = glob(["src/main/res/**"]),
    deps = android_deps + [
        "@org_slf4j_slf4j_android//jar",
        "@org_slf4j_slf4j_api//jar",
    ],
)
