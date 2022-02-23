import * as THREE from 'https://unpkg.com/three@0.126.1/build/three.module.js';
import Stats from 'https://unpkg.com/three@0.126.1/examples/jsm/libs/stats.module.js';
import { OrbitControls } from 'https://unpkg.com/three@0.126.1/examples/jsm/controls/OrbitControls.js';
import { FBXLoader } from 'https://unpkg.com/three@0.126.1/examples/jsm/loaders/FBXLoader.js';

let camera, scene, renderer, stats;
const animationActions = [];
const clock = new THREE.Clock();
let mixer;

start();

function start() {
    init();
}

function onWindowResize() {

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize( window.innerWidth, window.innerHeight );

}

//



// This function is required because of asynchronously model loading.
function play() {
    animationActions[0].play();
}

function init() {
     const container = document.createElement( 'div' );
    document.body.appendChild( container );

    camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 2000 );
    camera.position.set( 100, 200, 300 );

    scene = new THREE.Scene();
    scene.background = new THREE.Color( 0xa0a0a0 );
    // scene.fog = new THREE.Fog( 0xa0a0a0, 200, 1000 );

    // light
    const hemiLight = new THREE.HemisphereLight( 0xffffff, 0x444444 );
    hemiLight.position.set( 0, 200, 0 );
    scene.add( hemiLight );
    // const dirLight = new THREE.DirectionalLight( 0xffffff );
    // dirLight.position.set( 0, 200, 100 );
    // dirLight.castShadow = true;
    // dirLight.shadow.camera.top = 180;
    // dirLight.shadow.camera.bottom = - 100;
    // dirLight.shadow.camera.left = - 120;
    // dirLight.shadow.camera.right = 120;
    // scene.add( dirLight );

    // scene.add( new THREE.CameraHelper( dirLight.shadow.camera ) );

    // ground
    const mesh = new THREE.Mesh( new THREE.PlaneGeometry( 2000, 2000 ), new THREE.MeshPhongMaterial( { color: 0x999999, depthWrite: false } ) );
    mesh.rotation.x = - Math.PI / 2;
    mesh.receiveShadow = true;
    scene.add( mesh );

    const grid = new THREE.GridHelper( 2000, 20, 0x000000, 0x000000 );
    grid.material.opacity = 0.2;
    grid.material.transparent = true;
    scene.add( grid );

    // model
    const loader = new FBXLoader();
    loader.load( 'res/models/Samba Dancing.fbx', function ( object ) {
        mixer = new THREE.AnimationMixer( object );
        const action = mixer.clipAction( object.animations[ 1 ] );
        // action.timeScale = 2;
        // action.loop = THREE.LoopOnce;
        action.play();
        // animationActions[0] = action;
        // animationActions.push(action);
        // loader.load('DeathstrokeWalking.fbx', function(object) {
        //     mixer = new THREE.AnimationMixer(object);
        //     const action = mixer.clipAction(object.animations[0]);
        //     animationActions.push(action);
        //     action.play();
        // })
        object.traverse( function ( child ) {
            if ( child.isMesh ) {
                child.castShadow = true;
                child.receiveShadow = true;
            }
        } );
        let skeleton = object.children[0].children[2];
        // skeleton.rotation.x = 1;
        scene.add( object );
        console.log(object);
        animate();

        function animate() {
            requestAnimationFrame( animate );
            const delta = clock.getDelta();
            if ( mixer ) mixer.update( delta );
            if (skeleton.rotation.x < 0.6) {
                skeleton.rotation.x += 0.01 / 2;
            }
            else{
                if(object.children[0].rotation.x < 0.7){
                    object.children[0].rotation.x += 0.01 / 2;
                    object.children[0].children[3].rotation.x -= 0.01 / 2;
                    object.children[2].skeleton.bones[26].rotation.y += 0.018 / 2;
                    object.children[2].skeleton.bones[27].rotation.y += 0.022 / 2;
                    object.children[2].skeleton.bones[28].rotation.y += 0.006 / 2;
                }
            }
            renderer.render( scene, camera );
            // stats.update();
        }
    } );

    // loader.load('Soccer Trip.fbx', function (object) {
    //     // let duration = 20;
    //     // object.animations[0].duration = duration;
    //     // let timesKeyFrame = [];
    //     // let step = duration / 48;
    //     // for(let i = 0; i < 49; i++) {
    //     //     timesKeyFrame[i] = i * step;
    //     // }
    //     // let tracks = object.animations[0].tracks;
    //     // for(let i = 0; i < 2; i++) {
    //     //     object.animations[0].tracks[i].times = timesKeyFrame;
    //     // }
    //     // for (let i = 0; i < 6; i++) {
    //     //     object.animations[0].tracks[i].times = timesKeyFrame;
    //     // }
    //     // object.animations[0].tracks[50].times = timesKeyFrame;
    //     // object.animations[0].tracks[0].times = timesKeyFrame;
    //     // object.animations[0].tracks[1].times = timesKeyFrame;
    //     // object.animations[0].tracks[2].times = timesKeyFrame;
    //     const animationAction = mixer.clipAction(object.animations[0]);
    //     animationActions.push(animationAction);
    //     play();
    // });

    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setPixelRatio( window.devicePixelRatio );
    renderer.setSize( window.innerWidth, window.innerHeight );
    renderer.shadowMap.enabled = true;
    container.appendChild( renderer.domElement );

    // mouse control
    const controls = new OrbitControls( camera, renderer.domElement );
    controls.target.set( 0, 100, 0 );
    controls.update();
    window.addEventListener( 'resize', onWindowResize );

    // // stats
    // stats = new Stats();
    // container.appendChild( stats.dom );
}